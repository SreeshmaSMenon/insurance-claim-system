package com.hcl.insuranceclaimsystem.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hcl.insuranceclaimsystem.dto.ClaimDetailsResponse;
import com.hcl.insuranceclaimsystem.dto.HospitalDetail;
import com.hcl.insuranceclaimsystem.exception.AilmentNotFoundException;
import com.hcl.insuranceclaimsystem.exception.ClaimException;
import com.hcl.insuranceclaimsystem.exception.ClaimsNotFoundException;
import com.hcl.insuranceclaimsystem.exception.HospitalNotFoundException;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;
import com.hcl.insuranceclaimsystem.service.ClaimService;
import com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryInput;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryOutput;
import com.hcl.insuranceclaimsystem.dto.CommonResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * This class contains have the method for get List of claims by the userId.
 * 
 * @author KiruthikaK
 * @author Sairam
 * @since 2019/10/21
 *
 */
@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class ClaimController {

	@Autowired
	ClaimService claimService;

	/**
	 * This method used for get list of claims based on the userId.
	 * 
	 * @param userId
	 * @return List<ClaimResponse>
	 * @throws ClaimsNotFoundException
	 * @throws UserNotFoundException
	 */
	@GetMapping("/users/{userId}/claims")
	public ResponseEntity<List<ClaimDetailsResponse>> getClaims(@PathVariable Integer userId)
			throws ClaimsNotFoundException, UserNotFoundException {
		log.info(InsuranceClaimSystemConstants.CLAIM_INFO_START_CONTROLLER);
		Optional<List<ClaimDetailsResponse>> claimResponses = claimService.getClaims(userId);
		if (!claimResponses.isPresent()) {
			throw new ClaimsNotFoundException(InsuranceClaimSystemConstants.CLAIM_LIST_EMPTY);
		}
		log.info(InsuranceClaimSystemConstants.CLAIM_INFO_END_CONTROLLER);
		return new ResponseEntity<>(claimResponses.get(), HttpStatus.OK);

	}

	/**
	 * This method for get all the hospitalDetails.
	 * 
	 * @return List<HospitalDetails>
	 * @throws HospitalNotFoundException 
	 * @throws CommonException
	 */
	@GetMapping("/hospitals")
	public ResponseEntity<List<HospitalDetail>> getAllHospitals() throws HospitalNotFoundException  {
		log.info(InsuranceClaimSystemConstants.GET_HOSPITAL_INFO_START_CONTROLLER);
		Optional<List<HospitalDetail>> hospitalDetails = claimService.getAllHospitals();
		if (!hospitalDetails.isPresent()) {
			throw new HospitalNotFoundException(InsuranceClaimSystemConstants.HOSPITAL_NOT_FOUND);
		}
		log.info(InsuranceClaimSystemConstants.GET_HOSPITAL_INFO_END_CONTROLLER);
		return new ResponseEntity<>(hospitalDetails.get(), HttpStatus.OK);
	}

	/**
	 * This method for track the status for the particular claim.
	 * 
	 * @param claimId
	 * @return String
	 */
	@GetMapping("/claims/{claimId}/status")
	public ResponseEntity<CommonResponse> trackClaim(@PathVariable Integer claimId) {
		log.info(InsuranceClaimSystemConstants.TRACK_STATUS_INFO_START_CONTROLLER);
		CommonResponse commonResponse=new CommonResponse();
		String status = claimService.trackClaim(claimId);
		commonResponse.setStatusCode(HttpStatus.OK.value());
		commonResponse.setStatusMessage(status);
		log.info(InsuranceClaimSystemConstants.TRACK_STATUS_INFO_END_CONTROLLER);
		return new ResponseEntity<>(commonResponse, HttpStatus.OK);
	}

	/**
	 * claimEntry is create the clime with required details
	 * 
	 * @param claimEntryInput
	 * @return ClaimEntryOutput
	 * @throws AilmentNotFoundException 
	 * @throws ClaimException 
	 */
	@PostMapping(value = "/claims")
	public ResponseEntity<ClaimEntryOutput> claimEntry(@RequestBody ClaimEntryInput claimEntryInput) throws ClaimException, AilmentNotFoundException
			 {
		log.info(InsuranceClaimSystemConstants.CLAIM_ENTRY_CONTROLLER_STRAT);
		return ResponseEntity.status(HttpStatus.OK).body(claimService.claimEntry(claimEntryInput));
	}

}
