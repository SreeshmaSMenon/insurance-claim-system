package com.hcl.insuranceclaimsystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.insuranceclaimsystem.dto.LoginRequest;
import com.hcl.insuranceclaimsystem.entity.User;
import com.hcl.insuranceclaimsystem.repository.UserRepository;
import com.hcl.insuranceclaimsystem.util.InsuranceClaimSystemConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * This class contains the method for login.
 * 
 * @author Sreeshma S Menon
 * @since 2109/10/21
 *
 */

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	UserRepository userRepository;

	/**
	 * This method will accept loginRequest as input and get user object for given
	 * user name and password.Then return optional of user.
	 * 
	 * @param loginRequest
	 * @return optional of User
	 */
	@Override
	public Optional<User> getUser(LoginRequest loginRequest) {
		log.info(InsuranceClaimSystemConstants.LOGIN_INFO_START_SERVICE);
		Optional<User> optionalUser = userRepository.findByEmailAndPassword(loginRequest.getEmail(),
				loginRequest.getPassword());
		log.info(InsuranceClaimSystemConstants.LOGIN_INFO_END_SERVICE);
		return optionalUser;
	}
}
