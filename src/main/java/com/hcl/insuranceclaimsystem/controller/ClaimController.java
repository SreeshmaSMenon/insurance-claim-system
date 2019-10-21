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

import com.hcl.insuranceclaimsystem.dto.ClaimResponse;
import com.hcl.insuranceclaimsystem.exception.ClaimsNotFoundException;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;
import com.hcl.insuranceclaimsystem.service.ClaimService;
import com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class ClaimController {

	@Autowired
	ClaimService claimService;

	@GetMapping("/users/{userId}/claims")
	public ResponseEntity<List<ClaimResponse>> getClaims(@PathVariable Integer userId) throws ClaimsNotFoundException, UserNotFoundException {
		log.info(InsuranceClaimSystemConstants.CLAIM_INFO_START_CONTROLLER);
		Optional<List<ClaimResponse>> claimResponses = claimService.getClaims(userId);
		claimResponses.orElseThrow(()->new ClaimsNotFoundException(InsuranceClaimSystemConstants.CLAIM_LIST_EMPTY));
		
		log.info(InsuranceClaimSystemConstants.CLAIM_INFO_END_CONTROLLER);
		return new ResponseEntity<>(claimResponses.get(), HttpStatus.OK);

	}

}
