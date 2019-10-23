package com.hcl.insuranceclaimsystem.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.insuranceclaimsystem.dto.LoginRequest;
import com.hcl.insuranceclaimsystem.dto.LoginResponse;
import com.hcl.insuranceclaimsystem.entity.User;
import com.hcl.insuranceclaimsystem.exception.UserException;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;
import com.hcl.insuranceclaimsystem.service.LoginService;
import com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants;

import lombok.extern.slf4j.Slf4j;
/**
 * Controller for login to the application.
 * @since 2019/10/21
 * @author Sreeshma S Menon
 */
@Slf4j
@RestController
@CrossOrigin(allowedHeaders = {"*","*/"}, origins = {"*","*/"})
public class LoginController {
	
	@Autowired
	LoginService loginService;
	/**
	 * Method to login to the application if proper credentials found.
	 * @param loginRequest which includes the credentials details for login.
	 * @param bindingResult Which includes Request body parameters.
	 * @throws UserException will throw if the parameters of bindingResult is empty or null.
	 * @throws UserNotFoundException will throw if the given credentials not found.
	 * @return ResponseEntity of LoginResponse which contains successCode and successMessage.
	 */
    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) throws UserException, UserNotFoundException {
    	log.info(InsuranceClaimSystemConstants.LOGIN_INFO_START_CONTROLLER);
    	 LoginResponse loginResponse=new LoginResponse();
        if (bindingResult.hasErrors()) {
        	throw new UserException(bindingResult.getFieldError().getField()+InsuranceClaimSystemConstants.SEPERATOR+bindingResult.getFieldError().getDefaultMessage());
        }
        Optional<User> optionalUser=loginService.getUser(loginRequest);
        if(!optionalUser.isPresent()) {
        	throw new UserNotFoundException(InsuranceClaimSystemConstants.USER_NOT_FOUND);
        }
		loginResponse.setStatusCode(HttpStatus.OK.value());
		loginResponse.setMessage(InsuranceClaimSystemConstants.SUCCESS);
		loginResponse.setUserId(optionalUser.get().getUserId());
        log.info(InsuranceClaimSystemConstants.LOGIN_INFO_END_CONTROLLER);
		return new ResponseEntity<>(loginResponse, HttpStatus.CREATED);
	}
}
