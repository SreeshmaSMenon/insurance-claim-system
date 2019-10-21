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
 * This class includes method for login
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
	 * This method will accept LoginRequest as input login successfully if match.
	 * @param loginRequest 
	 * @param bindingResult
	 * @exception UserException
	 * @exception UserNotFoundException
	 * @return ResponseEntity of LoginResponse 
	 */
    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) throws UserException, UserNotFoundException {
    	log.debug(InsuranceClaimSystemConstants.LOGIN_DEBUG_END_CONTROLLER);
    	log.info("inside login email:{} password:{}",loginRequest.getEmail(),loginRequest.getPassword());
    	 LoginResponse loginResponse=new LoginResponse();
        if (bindingResult.hasErrors()) {
        	throw new UserException(bindingResult.getFieldError().getField()+" "+bindingResult.getFieldError().getDefaultMessage());
        }
        Optional<User> optionalUser=loginService.getUser(loginRequest);
        if(optionalUser.isPresent()) {
        	loginResponse.setStatusCode(HttpStatus.OK.value());
        	loginResponse.setMessage(InsuranceClaimSystemConstants.SUCCESS);
        	loginResponse.setUserId(optionalUser.get().getUserId());
        }else {
        	throw new UserNotFoundException(InsuranceClaimSystemConstants.USER_NOT_FOUND);
        }
        log.debug(InsuranceClaimSystemConstants.LOGIN_DEBUG_START_CONTROLLER);
        return new ResponseEntity<>(loginResponse,HttpStatus.CREATED);
    }
}
