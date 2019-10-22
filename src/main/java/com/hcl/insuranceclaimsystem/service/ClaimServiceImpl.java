package com.hcl.insuranceclaimsystem.service;

import static com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants.CLAIM_ENTRY_SUCCSES;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hcl.insuranceclaimsystem.dto.ClaimDetailsResponse;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryInput;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryOutput;
import com.hcl.insuranceclaimsystem.dto.HospitalDetails;
import com.hcl.insuranceclaimsystem.entity.Ailment;
import com.hcl.insuranceclaimsystem.entity.Claim;
import com.hcl.insuranceclaimsystem.entity.HospitalDetail;
import com.hcl.insuranceclaimsystem.entity.Insurance;
import com.hcl.insuranceclaimsystem.exception.CommonException;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;
import com.hcl.insuranceclaimsystem.repository.AilmentRepository;
import com.hcl.insuranceclaimsystem.repository.ClaimDetailRepository;
import com.hcl.insuranceclaimsystem.repository.ClaimRepository;
import com.hcl.insuranceclaimsystem.repository.HospitalDetailRepository;
import com.hcl.insuranceclaimsystem.repository.InsuranceRepository;
import com.hcl.insuranceclaimsystem.repository.UserRepository;
import com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * This class contains the method for get list of claims based on the userId.
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
	HospitalDetailRepository hospitalDetailRepository;
	@Autowired
	ClaimDetailRepository claimDetailRepository;
	@Autowired
	InsuranceRepository insuranceRepository;
	@Autowired
	AilmentRepository ailmentRepository;

	/**
	 * This method for get the all claims for the login user based on the roles.
	 * 
	 * @param userId
	 * @return Optional<List<ClaimResponse>>
	 * @throws UserNotFoundException
	 */
	@Transactional
	public Optional<List<ClaimDetailsResponse>> getClaims(Integer userId) throws UserNotFoundException {
		log.info(InsuranceClaimSystemConstants.CLAIM_INFO_START_SERVICE);
		Optional<String> role = userRepository.getUserRole(userId);
		ClaimDetailsResponse claimResponse = new ClaimDetailsResponse();
		if (!role.isPresent()) {
			throw new UserNotFoundException(InsuranceClaimSystemConstants.USER_NOT_FOUND);
		}
		List<ClaimDetailsResponse> claimResponses = new ArrayList<>();
		List<Claim> claims = new ArrayList<>();
		if (role.get().equalsIgnoreCase(InsuranceClaimSystemConstants.FIRST_LEVEL_APPROVER)) {
			claims = claimRepository.findByClaimStatus(InsuranceClaimSystemConstants.PENDING);
		} else if (role.get().equalsIgnoreCase(InsuranceClaimSystemConstants.SECOND_LEVEL_APPROVER)) {
			claims = claimRepository.findByClaimStatus(InsuranceClaimSystemConstants.FIRST_LEVEL_APPROVED);
		}
		claims.stream().forEach(claim -> {
			BeanUtils.copyProperties(claim, claimResponse);
			claimResponses.add(claimResponse);
		});
		log.info(InsuranceClaimSystemConstants.CLAIM_INFO_END_SERVICE);
		return Optional.of(claimResponses);
	}

	/**
	 * This Method for get all the Hospital details With in the network.
	 * 
	 * @return List<HospitalDetails>
	 */
	@Transactional
	public Optional<List<HospitalDetails>> getAllHospitalDetails() {
		log.info(InsuranceClaimSystemConstants.GET_HOSPITAL_INFO_START_SERVICE);
		List<HospitalDetail> details = hospitalDetailRepository.findAll();
		List<HospitalDetails> hospitalDetailsList = new ArrayList<>();
		details.stream().forEach(detail -> {
			HospitalDetails hospitalDetails = new HospitalDetails();
			hospitalDetails.setLabel(detail.getHospitalName());
			hospitalDetails.setValue(detail.getHospitalName());
			hospitalDetailsList.add(hospitalDetails);
		});
		log.info(InsuranceClaimSystemConstants.GET_HOSPITAL_INFO_END_SERVICE);
		return Optional.of(hospitalDetailsList);
	}

	/**
	 * This method for track the status for particular claim based on the claimId.
	 * 
	 * @param claimId
	 * @return List<string>
	 */
	@Transactional
	public List<String> trackClaim(Integer claimId) {
		log.info(InsuranceClaimSystemConstants.TRACK_STATUS_INFO_START_SERVICE);
		List<String> statusList=new ArrayList<>();
		statusList = claimDetailRepository.findByClaimId(claimId);
		log.info(InsuranceClaimSystemConstants.TRACK_STATUS_INFO_END_SERVICE);
		return statusList;
	}

	@Value("${file.upload-dir}")
	private String url;

	/**
	 * claim entry will create the claim with required details
	 * 
	 * @param ClaimEntryInput
	 * @return ClaimEntryOutput
	 * @throws CommonException
	 */
	@Override
	public ClaimEntryOutput claimEntry(ClaimEntryInput claimEntryInput) throws CommonException {

		log.info(InsuranceClaimSystemConstants.CLAIM_ENTRY_SERVICE_STRAT);
		Optional<Insurance> insuranceOptional = insuranceRepository.findById(claimEntryInput.getInsuranceNumber());
		if (!insuranceOptional.isPresent())
			throw new CommonException(InsuranceClaimSystemConstants.INVALID_INSURANCE_NUMBER);
		if (claimEntryInput.getAdmissionDate().isAfter(claimEntryInput.getDischargeDate()))
			throw new CommonException(InsuranceClaimSystemConstants.INVALID_DATE_RANGE);
		if (claimEntryInput.getTotalClaimAmount() < 0)
			throw new CommonException(InsuranceClaimSystemConstants.INVALID_DETAILS);
		Optional<Ailment> ailmentOptional = ailmentRepository.findByNatureOfAilment(claimEntryInput.getAilmentNature());
		if (!ailmentOptional.isPresent()) {
			throw new CommonException(InsuranceClaimSystemConstants.AILMENT_NOT_FOUND);
		}
		Optional<HospitalDetail> hospitalDetail = hospitalDetailRepository
				.findByHospitalName(claimEntryInput.getHospitalName());
		Double amout = claimEntryInput.getTotalClaimAmount();
		if (hospitalDetail.isPresent()) {
			amout = amout * (InsuranceClaimSystemConstants.NETWORK_HOSPITAL_PERCENTAGE);
		} else {
			amout = amout * (InsuranceClaimSystemConstants.OTHER_HOSPITAL_PERCENTAGE);
		}
		claimEntryInput.setTotalClaimAmount(amout);
		Claim claim = new Claim();
		BeanUtils.copyProperties(claimEntryInput, claim);
		claim.setClaimDate(LocalDateTime.now());
		claim.setClaimStatus(InsuranceClaimSystemConstants.CLAIM_PENDING);
		claimRepository.save(claim);
		ClaimEntryOutput claimEntryOutput = new ClaimEntryOutput();
		claimEntryOutput.setClaimId(claim.getClaimId());
		claimEntryOutput.setMessage(CLAIM_ENTRY_SUCCSES);
		claimEntryOutput.setStatusCode(HttpStatus.OK.value());
		log.info(InsuranceClaimSystemConstants.CLAIM_ENTRY_SERVICE_END);
		return claimEntryOutput;
	}

}
