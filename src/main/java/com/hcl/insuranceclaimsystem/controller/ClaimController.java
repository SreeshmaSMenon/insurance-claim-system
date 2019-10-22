package com.hcl.insuranceclaimsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.insuranceclaimsystem.dto.ClaimEntryInput;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryOutput;
import com.hcl.insuranceclaimsystem.exception.CommonException;
import com.hcl.insuranceclaimsystem.service.ClaimService;

import lombok.extern.slf4j.Slf4j;

/**
 * ClaimController will do the claim entry with required details
 * 
 * @author Sairam
 *
 */
@Slf4j
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/api")
public class ClaimController {

	@Autowired
	ClaimService claimService;

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
