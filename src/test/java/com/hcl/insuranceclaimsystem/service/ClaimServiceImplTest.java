package com.hcl.insuranceclaimsystem.service;

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

import com.hcl.insuranceclaimsystem.dto.ClaimDetailsResponse;
import com.hcl.insuranceclaimsystem.dto.HospitalDetails;
import com.hcl.insuranceclaimsystem.entity.Claim;
import com.hcl.insuranceclaimsystem.entity.ClaimDetail;
import com.hcl.insuranceclaimsystem.entity.HospitalDetail;
import com.hcl.insuranceclaimsystem.entity.Role;
import com.hcl.insuranceclaimsystem.entity.User;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;
import com.hcl.insuranceclaimsystem.repository.ClaimDetailRepository;
import com.hcl.insuranceclaimsystem.repository.ClaimRepository;
import com.hcl.insuranceclaimsystem.repository.HospitalDetailRepository;
import com.hcl.insuranceclaimsystem.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class ClaimServiceImplTest {
	@InjectMocks
	ClaimServiceImpl claimServiceImpl;
	@Mock
	ClaimRepository claimRepository;
	@Mock
	UserRepository userRepository;
	@Mock
	HospitalDetailRepository hospitalDetailRepository;
	@Mock
	ClaimDetailRepository claimDetailRepository;
	Integer userId;
	List<ClaimDetailsResponse> claimResponses;
	ClaimDetailsResponse claimResponse;
	User user;
	Role role;
	Claim claim;
	Claim claim2;
	List<Claim> claims;
	String userRole;
	Integer claimId;
	ClaimDetail claimDetail;
	String status;
	List<String> statusList;
	List<HospitalDetail> details;
	HospitalDetail hospitalDetail;

	@Before
	public void setup() {
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
		userRole = "FIRST_LEVEL_APPROVER";
		claim2 = new Claim();
		claim = new Claim();
		claim.setClaimId(1);
		claim.setClaimStatus("PENDING");
		claims = new ArrayList<>();
		claims.add(claim);

		claimId = 1;
		statusList = new ArrayList<>();
		hospitalDetail = new HospitalDetail();
		hospitalDetail.setHospitalId(1);
		details = new ArrayList<>();
		details.add(hospitalDetail);
		claimDetail = new ClaimDetail();
		claimDetail.setApprovalStatus("pending");
		status = claimDetail.getApprovalStatus();
		statusList.add(status);
	}

	@Test
	public void testGetClaims() throws UserNotFoundException {
		Mockito.when(userRepository.getUserRole(userId)).thenReturn(Optional.of(userRole));
		Mockito.when(claimRepository.findByClaimStatus(claim.getClaimStatus())).thenReturn(claims);
		Optional<List<ClaimDetailsResponse>> claimResponses = claimServiceImpl.getClaims(userId);
		Assert.assertEquals(claims.get(0).getClaimId(), claimResponses.get().get(0).getClaimId());
	}

	/*
	 * @Test public void testGetClaimsSecondLevelApprover() throws
	 * UserNotFoundException { userId=2; user.setUserId(userId); user.setRoleId(2);
	 * userRole = "SECOND_LEVEL_APPROVER";
	 * claim2.setClaimStatus("SECOND_LEVEL_APPROVED"); claims.add(claim2);
	 * Mockito.when(userRepository.getUserRole(userId)).thenReturn(Optional.of(
	 * userRole));
	 * Mockito.when(claimRepository.findByClaimStatus(claim.getClaimStatus())).
	 * thenReturn(claims); Optional<List<ClaimDetailsResponse>> claimResponses =
	 * claimServiceImpl.getClaims(userId);
	 * Assert.assertEquals(claims.get(0).getClaimStatus(),
	 * claimResponses.get().get(0).getClaimStatus()); }
	 */

	@Test(expected = UserNotFoundException.class)
	public void testExpectedUserNotFoundException() throws UserNotFoundException {
		Mockito.when(userRepository.getUserRole(userId)).thenReturn(Optional.empty());
		Optional<List<ClaimDetailsResponse>> claimResponses = claimServiceImpl.getClaims(userId);
		Assert.assertEquals(claims.get(0).getClaimId(), claimResponses.get().get(0).getClaimId());
	}

	@Test
	public void testGetAllHospitalDetails() {
		Mockito.when(hospitalDetailRepository.findAll()).thenReturn(details);
		Optional<List<HospitalDetails>> hospitalDetailsList = claimServiceImpl.getAllHospitalDetails();
		Assert.assertEquals(details.get(0).getHospitalId(), hospitalDetailsList.get().get(0).getHospitalId());
	}

	@Test
	public void testTrackClaim() {
		Mockito.when(claimDetailRepository.findByClaimId(claimId)).thenReturn(statusList);
		List<String> actualStatusList = claimServiceImpl.trackClaim(claimId);
		Assert.assertEquals(statusList.toString(), actualStatusList.toString());
	}
}
