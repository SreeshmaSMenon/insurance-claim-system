package com.hcl.insuranceclaimsystem.service;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.hcl.insuranceclaimsystem.dto.ClaimApproveRequest;
import com.hcl.insuranceclaimsystem.entity.Claim;
import com.hcl.insuranceclaimsystem.entity.ClaimDetail;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;
import com.hcl.insuranceclaimsystem.repository.ClaimDetailRepository;
import com.hcl.insuranceclaimsystem.repository.ClaimRepository;
import com.hcl.insuranceclaimsystem.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	@Mock
	UserRepository userRepository;
	@Mock
	ClaimRepository claimRepository;
	@Mock
	ClaimDetailRepository claimDetailRepository;
	
	@InjectMocks
	UserServiceImpl userServiceImpl;
	ClaimApproveRequest claimApproveRequest;
	ClaimDetail claimDetail;
	Claim claim=new Claim();
	@Before
	public void setup() {
		claimApproveRequest = new ClaimApproveRequest();
		claimApproveRequest.setClaimId(1);
		claimApproveRequest.setClaimStatus("APPROVE");
		claimApproveRequest.setComments("Approving");
		claimDetail = new ClaimDetail();
		claimDetail.setApprovalDate(LocalDateTime.now());
		claimDetail.setApprovalStatus("Approved");
		claimDetail.setClaimId(1);
		claimDetail.setComments("Approved by first level");
		claim.setAdmissionDate(LocalDate.of(2019, 07, 10));
		claim.setDischargeDate(LocalDate.of(2019, 07, 20));
		claim.setClaimId(1);
		claim.setDischargeSummary("discharged");
		claim.setClaimDate(LocalDateTime.now());
		claim.setDiagnosis("Blood Test");
		claim.setHospitalName("Fortis");
		claim.setTotalClaimAmount(60000.0);

	}
	
	@Test
	public void testApproveClaim() throws UserNotFoundException {
		Mockito.when(userRepository.getUserRole(Mockito.anyInt())).thenReturn(Optional.of("FIRST_LEVEL_APPROVER"));
		Mockito.when(claimRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(claim));
		Mockito.when(claimRepository.updateStatus("FIRST_LEVEL_APPROVED", 1)).thenReturn(1);
		Mockito.when(claimDetailRepository.save(Mockito.any())).thenReturn(claimDetail);
		Optional<ClaimDetail>claimDetailOptional=userServiceImpl.approveClaim(1, claimApproveRequest);
		assertNotNull(claimDetailOptional);
	}
	@Test(expected = UserNotFoundException.class)
	public void testUserNotFound() throws UserNotFoundException {
		Mockito.when(userRepository.getUserRole(Mockito.anyInt())).thenReturn(Optional.empty());
		Optional<ClaimDetail>claimDetailOptional=userServiceImpl.approveClaim(1, claimApproveRequest);
		assertNotNull(claimDetailOptional);
	}
	@Test
	public void testFirstLevelReject() throws UserNotFoundException {
		claimApproveRequest.setClaimStatus("REJECT");
		Mockito.when(userRepository.getUserRole(Mockito.anyInt())).thenReturn(Optional.of("FIRST_LEVEL_APPROVER"));
		Mockito.when(claimRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(claim));
		Mockito.when(claimRepository.updateStatus(Mockito.any(), Mockito.anyInt())).thenReturn(1);
		Mockito.when(claimDetailRepository.save(Mockito.any())).thenReturn(claimDetail);
		Optional<ClaimDetail>claimDetailOptional=userServiceImpl.approveClaim(1, claimApproveRequest);
		assertNotNull(claimDetailOptional);
	}
	@Test
	public void testSecondLevelApprove() throws UserNotFoundException {
		claimApproveRequest.setClaimStatus("APPROVE");
		Mockito.when(userRepository.getUserRole(Mockito.anyInt())).thenReturn(Optional.of("SECOND_LEVEL_APPROVER"));
		Mockito.when(claimRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(claim));
		Mockito.when(claimRepository.updateStatus(Mockito.any(), Mockito.anyInt())).thenReturn(1);
		Mockito.when(claimDetailRepository.save(Mockito.any())).thenReturn(claimDetail);
		Optional<ClaimDetail>claimDetailOptional=userServiceImpl.approveClaim(1, claimApproveRequest);
		assertNotNull(claimDetailOptional);
	}
	@Test
	public void testSecondLevelReject() throws UserNotFoundException {
		claimApproveRequest.setClaimStatus("REJECT");
		Mockito.when(userRepository.getUserRole(Mockito.anyInt())).thenReturn(Optional.of("SECOND_LEVEL_APPROVER"));
		Mockito.when(claimRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(claim));
		Mockito.when(claimRepository.updateStatus(Mockito.any(), Mockito.anyInt())).thenReturn(1);
		Mockito.when(claimDetailRepository.save(Mockito.any())).thenReturn(claimDetail);
		Optional<ClaimDetail>claimDetailOptional=userServiceImpl.approveClaim(1, claimApproveRequest);
		assertNotNull(claimDetailOptional);
	}
	
	@Test
	public void testEligibleAmount() throws UserNotFoundException {
		claimApproveRequest.setClaimStatus("APPROVE");
		Mockito.when(userRepository.getUserRole(Mockito.anyInt())).thenReturn(Optional.of("FIRST_LEVEL_APPROVER"));
		Mockito.when(claimRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(claim));
		Mockito.when(claimRepository.getEligibleamount(Mockito.anyInt())).thenReturn(Optional.of(30000.0));
		Mockito.when(claimRepository.updateStatus(Mockito.any(), Mockito.anyInt())).thenReturn(1);
		Mockito.when(claimDetailRepository.save(Mockito.any())).thenReturn(claimDetail);
		Optional<ClaimDetail>claimDetailOptional=userServiceImpl.approveClaim(1, claimApproveRequest);
		assertNotNull(claimDetailOptional);
	}
	
}
