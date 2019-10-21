package com.hcl.insuranceclaimsystem.service;

import java.util.Optional;

import com.hcl.insuranceclaimsystem.dto.ClaimApproveRequest;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;

public interface UserService {
	Optional<String> approveClaim(Integer userId,ClaimApproveRequest claimApproveRequest)throws UserNotFoundException;
}
