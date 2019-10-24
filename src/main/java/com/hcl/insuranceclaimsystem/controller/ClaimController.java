package com.hcl.insuranceclaimsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryInput;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryOutput;
import com.hcl.insuranceclaimsystem.dto.CommonResponse;
import com.hcl.insuranceclaimsystem.exception.AilmentNotFoundException;
import com.hcl.insuranceclaimsystem.exception.ClaimException;
import com.hcl.insuranceclaimsystem.exception.ClaimsNotFoundException;
import com.hcl.insuranceclaimsystem.service.ClaimService;
import com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller to handle the request and response for claims operations.
 * 
 * @author KiruthikaK
 * @author Sairam
 * @since 2019/10/21
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/claims")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class ClaimController {

	@Autowired
	ClaimService claimService;

	/**
	 * Method to call service to track the status of the given claim.
	 * 
	 * @param claimId this is the id of claim for which status to be return.
	 * @return String which is the current status of claim.
	 * @throws ClaimsNotFoundException will throw if requested claim not found.
	 */
	@GetMapping("/{claimId}/status")
	public ResponseEntity<CommonResponse> trackClaim(@PathVariable Integer claimId) throws ClaimsNotFoundException {
		log.info(InsuranceClaimSystemConstants.TRACK_STATUS_INFO_START_CONTROLLER);
		CommonResponse commonResponse = new CommonResponse();
		String status = claimService.trackClaim(claimId);
		commonResponse.setStatusCode(HttpStatus.OK.value());
		commonResponse.setStatusMessage(status);
		log.info(InsuranceClaimSystemConstants.TRACK_STATUS_INFO_END_CONTROLLER);
		return new ResponseEntity<>(commonResponse, HttpStatus.OK);
	}

	/**
	 * Method to call service to create a claim entry with given details.
	 * @param claimEntryInput which includes details required to create claim entry.
	 * @return ClaimEntryOutput which includes response details for the created claim entry.
	 * @throws AilmentNotFoundException will throw if ailment for the given claim is not present.
	 * @throws ClaimException will throw if given policy number is invalid, if given claim amount
	 *         is invalid, if given admission and discharge date range is invalid.
	 */
	@PostMapping(value = "/")
	public ResponseEntity<ClaimEntryOutput> claimEntry(@RequestBody ClaimEntryInput claimEntryInput)
			throws ClaimException, AilmentNotFoundException {
		log.info(InsuranceClaimSystemConstants.CLAIM_ENTRY_CONTROLLER_STRAT);
		return ResponseEntity.status(HttpStatus.OK).body(claimService.claimEntry(claimEntryInput));
	}

}
