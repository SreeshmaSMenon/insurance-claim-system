package com.hcl.insuranceclaimsystem.service;

import java.util.List;
import java.util.Optional;

import com.hcl.insuranceclaimsystem.dto.ClaimResponse;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;

public interface ClaimService {

	public Optional<List<ClaimResponse>> getClaims(Integer userId) throws UserNotFoundException;

}
