package com.hcl.insuranceclaimsystem.service;

import java.util.Optional;

import com.hcl.insuranceclaimsystem.dto.LoginRequest;
import com.hcl.insuranceclaimsystem.entity.User;
/**
 * 
 * Service interface to login to the application.
 * The preferred implementation is {@code AilmentServiceImpl}.
 * @author Sreeshma S Menon
 *
 */
public interface LoginService {
	/**
	 * Login to the application if proper credentials found.
	 * @param loginRequest which includes the credentials details for login.
	 * @return Optional of User entity for given credentials.
	 */
	 public Optional<User> getUser(LoginRequest loginRequest);
}
