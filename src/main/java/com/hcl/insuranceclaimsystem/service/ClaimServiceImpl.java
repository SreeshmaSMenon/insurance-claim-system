package com.hcl.insuranceclaimsystem.service;

import static com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants.CLAIM_ENTRY_SUCCSES;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hcl.insuranceclaimsystem.dto.ClaimEntryInput;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryOutput;
import com.hcl.insuranceclaimsystem.entity.Ailment;
import com.hcl.insuranceclaimsystem.entity.Claim;
import com.hcl.insuranceclaimsystem.entity.ClaimDetail;
import com.hcl.insuranceclaimsystem.entity.Hospital;
import com.hcl.insuranceclaimsystem.entity.Insurance;
import com.hcl.insuranceclaimsystem.exception.AilmentNotFoundException;
import com.hcl.insuranceclaimsystem.exception.ClaimException;
import com.hcl.insuranceclaimsystem.exception.ClaimsNotFoundException;
import com.hcl.insuranceclaimsystem.repository.AilmentRepository;
import com.hcl.insuranceclaimsystem.repository.ClaimDetailRepository;
import com.hcl.insuranceclaimsystem.repository.ClaimRepository;
import com.hcl.insuranceclaimsystem.repository.HospitalDetailRepository;
import com.hcl.insuranceclaimsystem.repository.InsuranceRepository;
import com.hcl.insuranceclaimsystem.repository.UserRepository;
import com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of ClaimService for claim operations.
 * 
 * @author KiruthikaK
 * @author sairam
 * @since 2019/10/21
 *
 */
@Slf4j
@Service
public class ClaimServiceImpl implements ClaimService {
	@Autowired
	ClaimRepository claimRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ClaimDetailRepository claimDetailRepository;
	@Autowired
	InsuranceRepository insuranceRepository;
	@Autowired
	AilmentRepository ailmentRepository;
	@Autowired
	HospitalDetailRepository hospitalDetailRepository;

	/**
	 * Method to track the status of the given claim.
	 * @param claimId this is the id of claim for which status to be return.
	 * @return String which is the current status of claim.
	 * @throws ClaimsNotFoundException will throw if requested claim not found.
	 */
	@Override
	public String trackClaim(Integer claimId) throws ClaimsNotFoundException {
		log.info(InsuranceClaimSystemConstants.TRACK_STATUS_INFO_START_SERVICE);
		Optional<Claim> claim = claimRepository.findById(claimId);
		if (!claim.isPresent()) {
			throw new ClaimsNotFoundException(InsuranceClaimSystemConstants.CLAIM_NOT_FOUND);
		}

		log.info(InsuranceClaimSystemConstants.TRACK_STATUS_INFO_END_SERVICE);
		return claim.get().getClaimStatus();
	}

	/**
	 * Service Implementation method to create a claim entry with given details .
	 * @param claimEntryInput which includes details required to create claim entry.
	 * @return ClaimEntryOutput which includes response details for the created claim entry.
	 * @throws AilmentNotFoundException will throw if ailment for the given claim is not present.
	 * @throws ClaimException           will throw if given policy number is
	 *                                  invalid, if given claim amount is invalid,
	 *                                  if given admission and discharge date range
	 *                                  is invalid.
	 */
	@Transactional
	public ClaimEntryOutput claimEntry(ClaimEntryInput claimEntryInput)
			throws ClaimException, AilmentNotFoundException {

		log.info(InsuranceClaimSystemConstants.CLAIM_ENTRY_SERVICE_STRAT);
		Optional<Insurance> insuranceOptional = insuranceRepository.findById(claimEntryInput.getInsuranceNumber());
		if (!insuranceOptional.isPresent())
			throw new ClaimException(InsuranceClaimSystemConstants.INVALID_INSURANCE_NUMBER);
		if (claimEntryInput.getAdmissionDate().isAfter(claimEntryInput.getDischargeDate()))
			throw new ClaimException(InsuranceClaimSystemConstants.INVALID_DATE_RANGE);
		if (claimEntryInput.getTotalClaimAmount() < 0)
			throw new ClaimException(InsuranceClaimSystemConstants.INVALID_CLAIM_AMOUNT);
		Optional<Ailment> ailmentOptional = ailmentRepository.findByNatureOfAilment(claimEntryInput.getAilmentNature());
		if (!ailmentOptional.isPresent()) {
			throw new AilmentNotFoundException(InsuranceClaimSystemConstants.AILMENT_NOT_FOUND);
		}
		Optional<Hospital> hospitalDetail = hospitalDetailRepository
				.findByHospitalName(claimEntryInput.getHospitalName());
		Double amount = claimEntryInput.getTotalClaimAmount();
		if (hospitalDetail.isPresent()) {
			amount = amount * (InsuranceClaimSystemConstants.NETWORK_HOSPITAL_PERCENTAGE);
		} else {
			amount = amount * (InsuranceClaimSystemConstants.OTHER_HOSPITAL_PERCENTAGE);
		}

		claimEntryInput.setTotalClaimAmount(amount);
		Claim claim = new Claim();
		BeanUtils.copyProperties(claimEntryInput, claim);
		claim.setClaimDate(LocalDateTime.now());
		claim.setClaimStatus(InsuranceClaimSystemConstants.CLAIM_PENDING);
		claimRepository.save(claim);
		ClaimDetail claimDetail = new ClaimDetail();
		claimDetail.setApprovalDate(LocalDateTime.now());
		claimDetail.setApprovalStatus(InsuranceClaimSystemConstants.CLAIM_PENDING);
		claimDetail.setClaimId(claim.getClaimId());
		claimDetailRepository.save(claimDetail);
		ClaimEntryOutput claimEntryOutput = new ClaimEntryOutput();
		claimEntryOutput.setClaimId(claim.getClaimId());
		claimEntryOutput.setMessage(CLAIM_ENTRY_SUCCSES);
		claimEntryOutput.setStatusCode(HttpStatus.OK.value());
		log.info(InsuranceClaimSystemConstants.CLAIM_ENTRY_SERVICE_END);
		return claimEntryOutput;
	}

}
