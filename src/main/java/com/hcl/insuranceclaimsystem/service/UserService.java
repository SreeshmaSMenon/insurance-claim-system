package com.hcl.insuranceclaimsystem.service;

import java.util.Optional;

import com.hcl.insuranceclaimsystem.dto.ClaimApproveRequest;
import com.hcl.insuranceclaimsystem.entity.ClaimDetail;
import com.hcl.insuranceclaimsystem.exception.ClaimsNotFoundException;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;

public interface UserService {
	Optional<ClaimDetail> approveClaim(Integer userId,ClaimApproveRequest claimApproveRequest)throws UserNotFoundException, ClaimsNotFoundException;
}
