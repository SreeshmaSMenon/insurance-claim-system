package com.hcl.insuranceclaimsystem.service;

import java.util.List;
import java.util.Optional;

import com.hcl.insuranceclaimsystem.dto.ClaimDetailsResponse;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryInput;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryOutput;
import com.hcl.insuranceclaimsystem.dto.HospitalDetails;
import com.hcl.insuranceclaimsystem.exception.CommonException;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;

public interface ClaimService {
	public Optional<List<ClaimDetailsResponse>> getClaims(Integer userId) throws UserNotFoundException;
	public Optional<List<HospitalDetails>> getAllHospitalDetails();
	public ClaimEntryOutput claimEntry( ClaimEntryInput claimEntryInput) throws CommonException;
//	public ClaimEntryOutput claimEntryFile( MultipartFile file, Integer claimId) throws CommonException, IOException;
	public List<String> trackClaim(Integer claimId);

}
