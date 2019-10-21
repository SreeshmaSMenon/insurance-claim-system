package com.hcl.insuranceclaimsystem.service;

import java.util.Optional;

import com.hcl.insuranceclaimsystem.dto.LoginRequest;
import com.hcl.insuranceclaimsystem.entity.User;

public interface LoginService {
	  public Optional<User> getUser(LoginRequest loginRequest);
}
