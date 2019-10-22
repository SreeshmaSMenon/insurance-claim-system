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
import com.hcl.insuranceclaimsystem.dto.HospitalDetails;
import com.hcl.insuranceclaimsystem.exception.ClaimsNotFoundException;
import com.hcl.insuranceclaimsystem.exception.CommonException;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;
import com.hcl.insuranceclaimsystem.service.ClaimService;
import com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryInput;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryOutput;
import lombok.extern.slf4j.Slf4j;

/**
 * This class contains have the method for get List of claims by the userId.
 * 
 * @author KiruthikaK
 * @author Sairam
 * 
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
	 * @throws CommonException
	 */
	@GetMapping("/hospitalDetails")
	public ResponseEntity<List<HospitalDetails>> getAllHospitalDetails() throws CommonException {
		log.info(InsuranceClaimSystemConstants.GET_HOSPITAL_INFO_START_CONTROLLER);
		Optional<List<HospitalDetails>> hospitalDetails = claimService.getAllHospitalDetails();
		if (!hospitalDetails.isPresent()) {
			throw new CommonException(InsuranceClaimSystemConstants.HOSPITAL_LIST_EMPTY);
		}
		log.info(InsuranceClaimSystemConstants.GET_HOSPITAL_INFO_END_CONTROLLER);
		return new ResponseEntity<>(hospitalDetails.get(), HttpStatus.OK);
	}

	/**
	 * This method for track the status for the particular claim.
	 * 
	 * @param claimId
	 * @return List<string>
	 */
	@GetMapping("/claims/{claimId}/status")
	public ResponseEntity<List<String>> trackClaim(@PathVariable Integer claimId) {
		log.info(InsuranceClaimSystemConstants.TRACK_STATUS_INFO_START_CONTROLLER);
		List<String> statusList = claimService.trackClaim(claimId);
		log.info(InsuranceClaimSystemConstants.TRACK_STATUS_INFO_END_CONTROLLER);
		return new ResponseEntity<>(statusList, HttpStatus.OK);
	}

	/**
	 * claimEntry is create the clime with required details
	 * 
	 * @param claimEntryInput
	 * @return ClaimEntryOutput
	 * @throws CommonException
	 */
	@PostMapping(value = "/claims")
	public ResponseEntity<ClaimEntryOutput> claimEntry(@RequestBody ClaimEntryInput claimEntryInput)
			throws CommonException {
		log.info("ClaimController --->claimEntry");
		return ResponseEntity.status(HttpStatus.OK).body(claimService.claimEntry(claimEntryInput));
	}

//	@PostMapping(value = "/claimsFile")
//	public ResponseEntity<ClaimEntryOutput> claimEntryFile(
//			@RequestParam(value = "admissionDate", required = false) LocalDate admissionDate,
//			@RequestParam("ailmentNature") String ailmentNature, @RequestParam("customerName") String customerName,
//			@RequestParam("diagnosis") String diagnosis,
//			@RequestParam(value = "dischargeDate", required = false) LocalDate dischargeDate,
//			@RequestParam("dischargeSummary") String dischargeSummary,
//			@RequestParam("hospitalName") String hospitalName, @RequestParam("insuranceNumber") Integer insuranceNumber,
//			@RequestParam("totalClaimAmount") Double totalClaimAmount, @RequestParam("file") MultipartFile file)
//			throws CommonException, IOException {
//		log.info("ClaimController --->claimEntry");
//		ClaimEntryInput claimEntryInput = new ClaimEntryInput();
//		claimEntryInput.setAdmissionDate(admissionDate);
//		claimEntryInput.setAilmentNature(ailmentNature);
//		claimEntryInput.setCustomerName(customerName);
//		claimEntryInput.setDiagnosis(diagnosis);
//		claimEntryInput.setDischargeDate(dischargeDate);
//		claimEntryInput.setDischargeSummary(dischargeSummary);
//		claimEntryInput.setHospitalName(hospitalName);
//		claimEntryInput.setInsuranceNumber(insuranceNumber);
//		claimEntryInput.setTotalClaimAmount(totalClaimAmount);
//		ClaimEntryOutput claimEntryOutput = claimService.claimEntry(claimEntryInput);
//		claimService.claimEntryFile(file, claimEntryOutput.getClaimId());
//		return ResponseEntity.status(HttpStatus.OK).body(claimEntryOutput);
//	}

}
