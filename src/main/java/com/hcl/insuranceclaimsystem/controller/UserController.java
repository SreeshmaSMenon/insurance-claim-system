package com.hcl.insuranceclaimsystem.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.insuranceclaimsystem.dto.ClaimApproveRequest;
import com.hcl.insuranceclaimsystem.dto.CommonResponse;
import com.hcl.insuranceclaimsystem.exception.UserException;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;
import com.hcl.insuranceclaimsystem.service.UserService;
import com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * This class contains method for user operations
 * @author Sreeshma S Menon
 * @see 2019/10/21
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;

	/**
	  * This method will call respective service layer method in order to approve given claim.
	  * @param claimApproveRequest
	  * @param bindingResult
	  * @param userId
	  * @return ResponseEntity<CommonResponse>
	  * @throws UserException
	  * @throws UserNotFoundException 
	  */
	@PostMapping("/{userId}/claims")
	 public ResponseEntity<CommonResponse> approveClaim(@Valid @RequestBody ClaimApproveRequest claimApproveRequest,@PathVariable("userId") Integer userId,BindingResult bindingResult) throws UserException, UserNotFoundException{
		 log.info(InsuranceClaimSystemConstants.APPROVE_DEBUG_START_CONTROLLER);
		 if(bindingResult.hasErrors()) {
			 throw new UserException(bindingResult.getFieldError().getField() + " " + bindingResult.getFieldError().getDefaultMessage());
		 }
		 CommonResponse commonResponse=new CommonResponse();
		 Optional<String>updateStatus= userService.approveClaim(userId,claimApproveRequest);
		 if(updateStatus.isPresent() && updateStatus.get().equals(InsuranceClaimSystemConstants.SUCCESS)){
			 commonResponse.setStatusCode(HttpStatus.OK.value());
			 commonResponse.setStatusMessage(InsuranceClaimSystemConstants.SUCCESS);
		 }
		 log.info(InsuranceClaimSystemConstants.APPROVE_DEBUG_END_CONTROLLER);
		 return new ResponseEntity<CommonResponse>(HttpStatus.OK);
	 }

}
