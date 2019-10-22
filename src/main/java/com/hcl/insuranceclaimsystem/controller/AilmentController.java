package com.hcl.insuranceclaimsystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.insuranceclaimsystem.dto.AilmentData;
import com.hcl.insuranceclaimsystem.dto.AilmentResponse;
import com.hcl.insuranceclaimsystem.service.AilmentService;
import com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * This class contains method for user operations
 * 
 * @author Sreeshma S Menon
 * @see 2019/10/21
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/ailments")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class AilmentController {

	@Autowired
	AilmentService ailmentService;

	/**
	 * This method will retrieve all the ailment available from database. 
	 * @return ResponseEntity<AilmentResponse>
	 */
	@GetMapping("/")
	public ResponseEntity<AilmentResponse> getAllAilments() {
		log.info(InsuranceClaimSystemConstants.AILMENT_DEBUG_START_CONTROLLER);
		Optional<List<AilmentData>> ailmentListOptional = ailmentService.getAllAilment();
		AilmentResponse ailmentResponse = new AilmentResponse();
		if (ailmentListOptional.isPresent()) {
			ailmentResponse.setStatusCode(HttpStatus.OK.value());
			ailmentResponse.setStatusMessage(InsuranceClaimSystemConstants.SUCCESS);
			ailmentResponse.setAilmentList(ailmentListOptional.get());
		}
		log.info(InsuranceClaimSystemConstants.AILMENT_DEBUG_END_CONTROLLER);
		return new ResponseEntity<>(ailmentResponse, HttpStatus.OK);
	}

}
