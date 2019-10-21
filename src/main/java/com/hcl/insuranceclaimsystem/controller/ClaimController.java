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

}
