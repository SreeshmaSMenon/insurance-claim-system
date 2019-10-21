package com.hcl.insuranceclaimsystem.service;

import java.util.List;
import java.util.Optional;

import com.hcl.insuranceclaimsystem.dto.ClaimDetailsResponse;
import com.hcl.insuranceclaimsystem.dto.HospitalDetails;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryInput;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryOutput;
import com.hcl.insuranceclaimsystem.exception.CommonException;

public interface ClaimService {
	public Optional<List<ClaimDetailsResponse>> getClaims(Integer userId) throws UserNotFoundException;
	public Optional<List<HospitalDetails>> getAllHospitalDetails();
	public ClaimEntryOutput claimEntry( ClaimEntryInput claimEntryInput) throws CommonException;
	public List<String> trackClaim(Integer claimId);

}
