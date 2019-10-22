package com.hcl.insuranceclaimsystem.service;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import com.hcl.insuranceclaimsystem.dto.ClaimDetailsResponse;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryInput;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryOutput;
import com.hcl.insuranceclaimsystem.dto.HospitalDetails;
import com.hcl.insuranceclaimsystem.entity.Ailment;
import com.hcl.insuranceclaimsystem.entity.Claim;
import com.hcl.insuranceclaimsystem.entity.ClaimDetail;
import com.hcl.insuranceclaimsystem.entity.HospitalDetail;
import com.hcl.insuranceclaimsystem.entity.Insurance;
import com.hcl.insuranceclaimsystem.entity.Role;
import com.hcl.insuranceclaimsystem.entity.User;
import com.hcl.insuranceclaimsystem.exception.CommonException;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;
import com.hcl.insuranceclaimsystem.repository.AilmentRepository;
import com.hcl.insuranceclaimsystem.repository.ClaimDetailRepository;
import com.hcl.insuranceclaimsystem.repository.ClaimRepository;
import com.hcl.insuranceclaimsystem.repository.HospitalDetailRepository;
import com.hcl.insuranceclaimsystem.repository.InsuranceRepository;
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
	@Mock
	InsuranceRepository insuranceRepository;
	@Mock
	AilmentRepository ailmentRepository;
	Insurance insurance;
	ClaimEntryInput claimEntryInput;
	Integer userId;
	List<ClaimDetailsResponse> claimResponses;
	ClaimDetailsResponse claimResponse;
	User user;
	Role role;
	Claim claim;
	List<Claim> claims;
	String userRole;
	Integer claimId;
	ClaimDetail claimDetail;
	String status;
	List<String> statusList;
	List<HospitalDetail> details;
	HospitalDetail hospitalDetail;

	Ailment ailment;

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
		insurance = new Insurance();
		insurance.setAddress("address");
		insurance.setCustomerName("Name");
		insurance.setDob(LocalDate.now());
		insurance.setInsuranceNumber(1);
		insurance.setMobileNumber(999999999L);
		claim = new Claim();
		claim.setClaimId(1);
		claim.setClaimStatus("PENDING");
		claim.setInsuranceNumber(insurance.getInsuranceNumber());
		claim.setAdmissionDate(LocalDate.now());
		claim.setAilmentNature("alinment");
		claim.setClaimDate(LocalDateTime.now());
		claim.setDiagnosis("diagnosis");
		claim.setDischargeDate(LocalDate.now().plusDays(1));
		claim.setDischargeSummary("summary");
		claim.setDocumentsPath("path");
		claim.setHospitalName("hospitalName");
		claim.setTotalClaimAmount(10000d);
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
		ailment = new Ailment();
		ailment.setAilmentId(1);
		ailment.setNatureOfAilment("HART");
		claimEntryInput = new ClaimEntryInput();
		claimEntryInput.setAdmissionDate(claim.getAdmissionDate());
		claimEntryInput.setAilmentNature(ailment.getNatureOfAilment());
		claimEntryInput.setCustomerName("name");
		claimEntryInput.setDiagnosis(claim.getDiagnosis());
		claimEntryInput.setTotalClaimAmount(claim.getTotalClaimAmount());
		claimEntryInput.setDischargeDate(claim.getDischargeDate());
		claimEntryInput.setInsuranceNumber(claim.getInsuranceNumber());

	}

	@Test
	public void testGetClaims() throws UserNotFoundException {
		Mockito.when(userRepository.getUserRole(userId)).thenReturn(Optional.of(userRole));
		Optional<List<ClaimDetailsResponse>> claimResponses = claimServiceImpl.getClaims(userId);
		assertNotNull(claimResponses);
	}

	
	@Test
	public void testGetClaimsSecondLevelApprover() throws UserNotFoundException {
		userId = 2;
		user.setUserId(userId);
		user.setRoleId(2);
		userRole = "SECOND_LEVEL_APPROVER";
		claim.setClaimStatus("SECOND_LEVEL_APPROVED");
		claims.add(claim);
		Mockito.when(userRepository.getUserRole(userId)).thenReturn(Optional.of(userRole));
		Optional<List<ClaimDetailsResponse>> claimResponses = claimServiceImpl.getClaims(userId);
		assertNotNull(claimResponses);
	}

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
		Assert.assertNotNull(hospitalDetailsList);
	}

	@Test
	public void testTrackClaim() {
		Mockito.when(claimDetailRepository.findByClaimId(claimId)).thenReturn(statusList);
		List<String> actualStatusList = claimServiceImpl.trackClaim(claimId);
		Assert.assertEquals(statusList.toString(), actualStatusList.toString());
	}

	@Test(expected = CommonException.class)
	public void testClaimEntry() throws CommonException {
		Mockito.when(insuranceRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(insurance));
		claimServiceImpl.claimEntry(claimEntryInput);

	}

	@Test(expected = CommonException.class)
	public void testClaimEntryNatureEliment() throws CommonException {
		claimEntryInput.setInsuranceNumber(claim.getInsuranceNumber());
		Mockito.when(insuranceRepository.findById(claim.getInsuranceNumber())).thenReturn(Optional.of(insurance));
		ClaimEntryOutput actual = claimServiceImpl.claimEntry(claimEntryInput);
		Assert.assertEquals(HttpStatus.OK.value(), actual.getStatusCode().intValue());

	}

	@Test(expected = CommonException.class)
	public void testClaimEntryDischargeDate() throws CommonException {
		claimEntryInput.setInsuranceNumber(claim.getInsuranceNumber());
		claimEntryInput.setDischargeDate(LocalDate.now().minusDays(2));
		Mockito.when(insuranceRepository.findById(claim.getInsuranceNumber())).thenReturn(Optional.of(insurance));
		ClaimEntryOutput actual = claimServiceImpl.claimEntry(claimEntryInput);
		Assert.assertEquals(HttpStatus.OK.value(), actual.getStatusCode().intValue());

	}

	@Test(expected = CommonException.class)
	public void testClaimEntryNagetiveAmount() throws CommonException {
		claimEntryInput.setInsuranceNumber(claim.getInsuranceNumber());
		claimEntryInput.setTotalClaimAmount(-100d);
		Mockito.when(insuranceRepository.findById(claim.getInsuranceNumber())).thenReturn(Optional.of(insurance));

		ClaimEntryOutput actual = claimServiceImpl.claimEntry(claimEntryInput);
		Assert.assertEquals(HttpStatus.OK.value(), actual.getStatusCode().intValue());

	}

	@Test
	public void testClaimEntryPositive() throws CommonException {
		claimEntryInput.setInsuranceNumber(claim.getInsuranceNumber());
		Mockito.when(insuranceRepository.findById(claim.getInsuranceNumber())).thenReturn(Optional.of(insurance));
		Mockito.when(ailmentRepository.findByNatureOfAilment(claimEntryInput.getAilmentNature()))
				.thenReturn(Optional.of(ailment));
		ClaimEntryOutput actual = claimServiceImpl.claimEntry(claimEntryInput);
		Assert.assertEquals(HttpStatus.OK.value(), actual.getStatusCode().intValue());

	}
	@Test
	public void testHospitalExist() throws CommonException {
		claimEntryInput.setInsuranceNumber(claim.getInsuranceNumber());
		Mockito.when(insuranceRepository.findById(claim.getInsuranceNumber())).thenReturn(Optional.of(insurance));
		Mockito.when(ailmentRepository.findByNatureOfAilment(claimEntryInput.getAilmentNature()))
				.thenReturn(Optional.of(ailment));
		Mockito.when(hospitalDetailRepository.findByHospitalName(Mockito.any())).thenReturn(Optional.empty());	
		ClaimEntryOutput actual = claimServiceImpl.claimEntry(claimEntryInput);
		Assert.assertEquals(HttpStatus.OK.value(), actual.getStatusCode().intValue());

	}
	@Test
	public void testHospitalOther() throws CommonException {
		claimEntryInput.setInsuranceNumber(claim.getInsuranceNumber());
		Mockito.when(insuranceRepository.findById(claim.getInsuranceNumber())).thenReturn(Optional.of(insurance));
		Mockito.when(ailmentRepository.findByNatureOfAilment(claimEntryInput.getAilmentNature()))
				.thenReturn(Optional.of(ailment));
		Mockito.when(hospitalDetailRepository.findByHospitalName(Mockito.any())).thenReturn(Optional.of(hospitalDetail));	
		ClaimEntryOutput actual = claimServiceImpl.claimEntry(claimEntryInput);
		Assert.assertEquals(HttpStatus.OK.value(), actual.getStatusCode().intValue());

	}
}
