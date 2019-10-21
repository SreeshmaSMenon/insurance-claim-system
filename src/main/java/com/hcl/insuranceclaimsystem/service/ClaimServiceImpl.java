package com.hcl.insuranceclaimsystem.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.insuranceclaimsystem.dto.ClaimDetailsResponse;
import com.hcl.insuranceclaimsystem.dto.HospitalDetails;
import com.hcl.insuranceclaimsystem.entity.Claim;
import com.hcl.insuranceclaimsystem.entity.HospitalDetail;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;
import com.hcl.insuranceclaimsystem.repository.ClaimDetailRepository;
import com.hcl.insuranceclaimsystem.repository.ClaimRepository;
import com.hcl.insuranceclaimsystem.repository.HospitalDetailRepository;
import com.hcl.insuranceclaimsystem.repository.UserRepository;
import com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * This class contains the method for get list of claims based on the userId.
 * 
 * @author KiruthikaK
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
		List<HospitalDetail> details = hospitalDetailRepository.findAll();
		List<HospitalDetails> hospitalDetailsList = new ArrayList<>();
		details.stream().forEach(detail -> {
			HospitalDetails hospitalDetails = new HospitalDetails();
			BeanUtils.copyProperties(detail, hospitalDetails);
			hospitalDetailsList.add(hospitalDetails);
		});
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
		List<String> statusList=new ArrayList<>();
		statusList = claimDetailRepository.findByClaimId(claimId);
		return statusList;
	}

}
