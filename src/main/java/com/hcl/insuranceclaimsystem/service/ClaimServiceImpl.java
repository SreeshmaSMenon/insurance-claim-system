package com.hcl.insuranceclaimsystem.service;

import static com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants.CLAIM_ENTRY_SUCCSES;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hcl.insuranceclaimsystem.dto.ClaimEntryInput;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryOutput;
import com.hcl.insuranceclaimsystem.entity.Claim;
import com.hcl.insuranceclaimsystem.entity.Insurance;
import com.hcl.insuranceclaimsystem.exception.CommonException;
import com.hcl.insuranceclaimsystem.repository.ClaimRepository;
import com.hcl.insuranceclaimsystem.repository.InsuranceRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sairam
 *
 */
@Slf4j
@Service
public class ClaimServiceImpl implements ClaimService {
	@Autowired
	ClaimRepository claimRepository;
	@Autowired
	InsuranceRepository insuranceRepository;

	/**
	 * claim entry will create the claim with required details
	 * 
	 * @param ClaimEntryInput
	 * @return ClaimEntryOutput
	 * @throws CommonException
	 */
	@Override
	public ClaimEntryOutput claimEntry(ClaimEntryInput claimEntryInput) throws CommonException {

		log.info("ClaimServiceImpl-->ClaimEntryOutput entry");
		log.info("records hospitalName:{} ", claimEntryInput.getHospitalName());
		Optional<Insurance> insuranceOptional = insuranceRepository.findById(claimEntryInput.getInsuranceNumber());
		if (!insuranceOptional.isPresent())
			throw new CommonException("invalid insurance Number");
		if (claimEntryInput.getAdmissionDate().isAfter(claimEntryInput.getDischargeDate()))
			throw new CommonException("admission uplo");
		if (claimEntryInput.getTotalClaimAmount() < 0)
			throw new CommonException("invalid deatils");

		Claim claim = new Claim();
		BeanUtils.copyProperties(claimEntryInput, claim);
		claim.setClaimDate(LocalDateTime.now());
		claimRepository.save(claim);

		ClaimEntryOutput claimEntryOutput = new ClaimEntryOutput();
		claimEntryOutput.setClaimId(claim.getClaimId());
		claimEntryOutput.setMessage(CLAIM_ENTRY_SUCCSES);
		claimEntryOutput.setStatusCode(HttpStatus.OK.value());
		log.info("ClaimServiceImpl-->ClaimEntryOutput ");
		return claimEntryOutput;
	}

	@Override
	public ClaimEntryOutput trackClaim(ClaimEntryInput claimEntryInput) throws CommonException {
		return null;
	}

}
