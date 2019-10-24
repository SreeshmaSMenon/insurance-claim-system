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
import com.hcl.insuranceclaimsystem.exception.AilmentNotFoundException;
import com.hcl.insuranceclaimsystem.service.AilmentService;
import com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for handling the requests and responses to retrieve aliments.
 * 
 * @author Sreeshma S Menon
 * @since 2019/10/21
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
	 * Method to call service method to retrieve ailments, if empty list found will
	 * throw AilmentNotFoundException.
	 * 
	 * @return AilmentResponse which consist of status message and ailment list.
	 * @throws AilmentNotFoundException will throw if empty list of ailments from
	 *                                  service.
	 */
	@GetMapping("/")
	public ResponseEntity<AilmentResponse> getAllAilments() throws AilmentNotFoundException {
		log.info(InsuranceClaimSystemConstants.AILMENT_INFO_START_CONTROLLER);
		Optional<List<AilmentData>> ailmentListOptional = ailmentService.getAllAilment();
		if (!ailmentListOptional.isPresent()) {
			throw new AilmentNotFoundException(InsuranceClaimSystemConstants.AILMENT_NOT_FOUND);
		}
		AilmentResponse ailmentResponse = new AilmentResponse();
		ailmentResponse.setStatusCode(HttpStatus.OK.value());
		ailmentResponse.setStatusMessage(InsuranceClaimSystemConstants.SUCCESS);
		ailmentResponse.setAilmentList(ailmentListOptional.get());
		log.info(InsuranceClaimSystemConstants.AILMENT_INFO_END_CONTROLLER);
		return new ResponseEntity<>(ailmentResponse, HttpStatus.OK);
	}

}
