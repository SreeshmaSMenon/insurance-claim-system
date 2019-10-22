package com.hcl.insuranceclaimsystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hcl.insuranceclaimsystem.dto.ClaimDetailsResponse;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryInput;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryOutput;
import com.hcl.insuranceclaimsystem.dto.CommonResponse;
import com.hcl.insuranceclaimsystem.dto.HospitalDetails;
import com.hcl.insuranceclaimsystem.entity.Claim;
import com.hcl.insuranceclaimsystem.entity.Role;
import com.hcl.insuranceclaimsystem.entity.User;
import com.hcl.insuranceclaimsystem.exception.ClaimsNotFoundException;
import com.hcl.insuranceclaimsystem.exception.CommonException;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;
import com.hcl.insuranceclaimsystem.service.ClaimService;

@RunWith(MockitoJUnitRunner.class)
public class ClaimControllerTest {

	@InjectMocks
	ClaimController claimController;
	@Mock
	ClaimService claimService;
	Integer userId;
	List<ClaimDetailsResponse> claimResponses;
	ClaimDetailsResponse claimResponse;
	User user;
	Role role;
	Claim claim;
	Integer claimId;
	HospitalDetails hospitalDetail;
	List<HospitalDetails> hospitalDetails;
	List<String> statusList;
	
	ClaimEntryInput claimEntryInput;
	ClaimEntryOutput  claimEntryOutput;
	CommonResponse commonResponse;
	@Before
	public void setup() {
		commonResponse=new CommonResponse();
		commonResponse.setStatusCode(200);
		commonResponse.setStatusMessage("PENDING");
		userId = 1;
		claimResponse = new ClaimDetailsResponse();
		claimResponse.setClaimId(1);
		claimResponses = new ArrayList<>();
		claimResponses.add(claimResponse);
		user = new User();
		user.setUserId(1);
		user.setRoleId(1);
		role = new Role();
		role.setRoleName("FIRST_LEVEL_APPROVER");
		role.setRoleId(1);
		claim = new Claim();
		claim.setClaimId(1);
		claim.setClaimStatus("PENDING");
		hospitalDetail = new HospitalDetails();
		hospitalDetails = new ArrayList<>();
		hospitalDetails.add(hospitalDetail);
		claimId = 1;
		statusList = new ArrayList<>();
		
		claimEntryInput = new ClaimEntryInput();
		claimEntryInput.setAdmissionDate(claim.getAdmissionDate());
		claimEntryInput.setAilmentNature("hart");
		claimEntryInput.setCustomerName("name");
		claimEntryInput.setDiagnosis(claim.getDiagnosis());
		claimEntryInput.setTotalClaimAmount(claim.getTotalClaimAmount());
		claimEntryInput.setDischargeDate(claim.getDischargeDate());
		claimEntryInput.setInsuranceNumber(claim.getInsuranceNumber());
	}

	@Test
	public void testGetClaims() throws UserNotFoundException, ClaimsNotFoundException {
		Mockito.when(claimService.getClaims(Mockito.anyInt())).thenReturn(Optional.of(claimResponses));
		ResponseEntity<List<ClaimDetailsResponse>> actual = claimController.getClaims(Mockito.anyInt());
		ResponseEntity<List<ClaimDetailsResponse>> expected = new ResponseEntity<>(Optional.of(claimResponses).get(),
				HttpStatus.OK);
		Assert.assertEquals(expected.getStatusCodeValue(), actual.getStatusCodeValue());
	}

	@Test(expected = ClaimsNotFoundException.class)
	public void testExpectedClaimsNotFoundException() throws UserNotFoundException, ClaimsNotFoundException {
		Mockito.when(claimService.getClaims(Mockito.anyInt())).thenReturn(Optional.empty());
		ResponseEntity<List<ClaimDetailsResponse>> actual = claimController.getClaims(Mockito.anyInt());
		ResponseEntity<List<ClaimDetailsResponse>> expected = new ResponseEntity<>(Optional.of(claimResponses).get(),
				HttpStatus.OK);
		Assert.assertEquals(expected.getStatusCodeValue(), actual.getStatusCodeValue());
	}

	@Test
	public void testGetAllHospitalDetails() throws CommonException {
		Mockito.when(claimService.getAllHospitalDetails()).thenReturn(Optional.of(hospitalDetails));
		ResponseEntity<List<HospitalDetails>> actual = claimController.getAllHospitalDetails();
		ResponseEntity<List<HospitalDetails>> expected = new ResponseEntity<>(hospitalDetails, HttpStatus.OK);
		Assert.assertEquals(expected.getStatusCodeValue(), actual.getStatusCodeValue());
	}

	@Test(expected = CommonException.class)
	public void testExpectedCommonException() throws CommonException {
		Mockito.when(claimService.getAllHospitalDetails()).thenReturn(Optional.empty());
		ResponseEntity<List<HospitalDetails>> actual = claimController.getAllHospitalDetails();
		ResponseEntity<List<HospitalDetails>> expected = new ResponseEntity<>(hospitalDetails, HttpStatus.OK);
		Assert.assertEquals(expected.getStatusCodeValue(), actual.getStatusCodeValue());
	}

	@Test
	public void testtrackClaim() {
		Mockito.when(claimService.trackClaim(claimId)).thenReturn("PENDING");
		ResponseEntity<CommonResponse> actual = claimController.trackClaim(claimId);
		ResponseEntity<CommonResponse> expected = new ResponseEntity<>(commonResponse, HttpStatus.OK);
		Assert.assertEquals(expected.getStatusCodeValue(), actual.getStatusCodeValue());

	}
	@Test
	public void testClaimEntry()
			throws CommonException {
		Mockito.when(claimService.claimEntry(claimEntryInput)).thenReturn(claimEntryOutput);
		ResponseEntity<ClaimEntryOutput> actual = claimController.claimEntry(claimEntryInput);
		Assert.assertEquals(HttpStatus.OK.value(), actual.getStatusCodeValue());

	}
}
