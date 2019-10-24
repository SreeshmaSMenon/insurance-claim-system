package com.hcl.insuranceclaimsystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.insuranceclaimsystem.dto.ClaimApproveRequest;
import com.hcl.insuranceclaimsystem.dto.ClaimDetailsResponse;
import com.hcl.insuranceclaimsystem.dto.CommonResponse;
import com.hcl.insuranceclaimsystem.entity.ClaimDetail;
import com.hcl.insuranceclaimsystem.exception.ClaimsNotFoundException;
import com.hcl.insuranceclaimsystem.exception.UserException;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;
import com.hcl.insuranceclaimsystem.service.ClaimService;
import com.hcl.insuranceclaimsystem.service.UserService;
import com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller to handle all request and responses for user operations
 * (to see all the claim requests to approve, approve the claim).
 * @author Sreeshma S Menon
 * @author Kiruthika K
 * @since 2019/10/21
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	ClaimService claimService;
	
	/**
	 * Method to call service method to retrieve all claim requests for given user id.
	 * @param userId which is the user id to retrieve respective claim requests.
	 * @return List of ClaimResponse which includes claim requests for given user id.
	 * @throws ClaimsNotFoundException will throw if no claim found.
	 * @throws UserNotFoundException will throw if the user is not found fot given user id.
	 */
	@GetMapping("/{userId}/claims")
	public ResponseEntity<List<ClaimDetailsResponse>> getClaims(@PathVariable Integer userId)
			throws ClaimsNotFoundException, UserNotFoundException {
		log.info(InsuranceClaimSystemConstants.CLAIM_INFO_START_CONTROLLER);
		Optional<List<ClaimDetailsResponse>> claimResponses = userService.getClaims(userId);
		List<ClaimDetailsResponse> details=new ArrayList<>();
		if (claimResponses.isPresent()) {
			details=claimResponses.get();
			if(details.isEmpty()) {
			  throw new ClaimsNotFoundException(InsuranceClaimSystemConstants.CLAIM_LIST_EMPTY);
			}
		}
		log.info(InsuranceClaimSystemConstants.CLAIM_INFO_END_CONTROLLER);
		return new ResponseEntity<>(details, HttpStatus.OK);
	}

	/**
	 *Method to call service method to approve claim with given clam id  for given user id. 
	 * @param claimApproveRequest which includes deatils required to approve the claim.
	 * @param bindingResult which includes claimApproveRequest parameters for the validation.
	 * @param userId which is id of the user who is going to approve the claim.
	 * @return ResponseEntity of commonResponse consist of status code and status message.
	 * @throws UserException will throw any there is any invalid inputs from the user.
	 * @throws UserNotFoundException will throw if the user with given id is not present.
	 * @throws ClaimsNotFoundException will throw if claim with given id is not present.
	 */
	@PutMapping("/{userId}/claims")
	public ResponseEntity<CommonResponse> approveClaim(@Valid @RequestBody ClaimApproveRequest claimApproveRequest,
			@PathVariable("userId") Integer userId, BindingResult bindingResult)
			throws UserException, UserNotFoundException, ClaimsNotFoundException {
		log.info(InsuranceClaimSystemConstants.APPROVE_INFO_START_CONTROLLER);
		if (bindingResult.hasErrors()) {
			throw new UserException(bindingResult.getFieldError().getField() + InsuranceClaimSystemConstants.SEPERATOR
					+ bindingResult.getFieldError().getDefaultMessage());
		}
		CommonResponse commonResponse = new CommonResponse();
		Optional<ClaimDetail> claimDetailOptional = userService.approveClaim(userId, claimApproveRequest);
		if (claimDetailOptional.isPresent()) {
			commonResponse.setStatusCode(HttpStatus.OK.value());
			commonResponse.setStatusMessage(InsuranceClaimSystemConstants.SUCCESS);
		}
		log.info(InsuranceClaimSystemConstants.APPROVE_INFO_END_CONTROLLER);
		return new ResponseEntity<>(commonResponse, HttpStatus.OK);
	}

}
