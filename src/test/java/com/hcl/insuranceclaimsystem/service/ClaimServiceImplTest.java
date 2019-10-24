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
import com.hcl.insuranceclaimsystem.entity.Ailment;
import com.hcl.insuranceclaimsystem.entity.Claim;
import com.hcl.insuranceclaimsystem.entity.ClaimDetail;
import com.hcl.insuranceclaimsystem.entity.Hospital;
import com.hcl.insuranceclaimsystem.entity.Insurance;
import com.hcl.insuranceclaimsystem.entity.Role;
import com.hcl.insuranceclaimsystem.entity.User;
import com.hcl.insuranceclaimsystem.exception.AilmentNotFoundException;
import com.hcl.insuranceclaimsystem.exception.ClaimException;
import com.hcl.insuranceclaimsystem.exception.ClaimsNotFoundException;
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
	List<ClaimDetailsResponse> claimResponses;
	ClaimDetailsResponse claimResponse;
	User user;
	Role role;
	Claim claim;
	List<Claim> claims;
	Integer claimId;
	ClaimDetail claimDetail;
	String status;
	List<String> statusList;
	List<Hospital> details;
	Hospital hospitalDetail;
	Ailment ailment;

	@Before
	public void setup() {
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
		hospitalDetail = new Hospital();
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
	public void testTrackClaim() throws ClaimsNotFoundException {
		claim.setClaimStatus("PENDING");
		Mockito.when(claimRepository.findById(claimId)).thenReturn(Optional.of(claim));
		String actualStatus = claimServiceImpl.trackClaim(claimId);
		Assert.assertEquals("PENDING", actualStatus);
	}

	@Test
	public void testClaimEntry() throws ClaimException, AilmentNotFoundException {
		Mockito.when(insuranceRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(insurance));
		Mockito.when(ailmentRepository.findByNatureOfAilment(Mockito.anyString())).thenReturn(Optional.of(ailment));
		ClaimEntryOutput claimEntryOutput=claimServiceImpl.claimEntry(claimEntryInput);
		assertNotNull(claimEntryOutput);

	}
	
	@Test(expected = ClaimException.class)
	public void testInvalidInsurance() throws ClaimException, AilmentNotFoundException {
		Mockito.when(insuranceRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		claimServiceImpl.claimEntry(claimEntryInput);

	}

	@Test(expected = AilmentNotFoundException.class)
	public void testClaimEntryNatureAilment() throws ClaimException, AilmentNotFoundException {
		claimEntryInput.setInsuranceNumber(claim.getInsuranceNumber());
		Mockito.when(insuranceRepository.findById(claim.getInsuranceNumber())).thenReturn(Optional.of(insurance));
		ClaimEntryOutput actual = claimServiceImpl.claimEntry(claimEntryInput);
		Assert.assertEquals(HttpStatus.OK.value(), actual.getStatusCode().intValue());

	}

	@Test(expected = ClaimException.class)
	public void testClaimEntryDischargeDate() throws ClaimException, AilmentNotFoundException  {
		claimEntryInput.setInsuranceNumber(claim.getInsuranceNumber());
		claimEntryInput.setDischargeDate(LocalDate.now().minusDays(2));
		Mockito.when(insuranceRepository.findById(claim.getInsuranceNumber())).thenReturn(Optional.of(insurance));
		ClaimEntryOutput actual = claimServiceImpl.claimEntry(claimEntryInput);
		Assert.assertEquals(HttpStatus.OK.value(), actual.getStatusCode().intValue());

	}

	@Test(expected = ClaimException.class)
	public void testClaimEntryNagetiveAmount() throws ClaimException, AilmentNotFoundException  {
		claimEntryInput.setInsuranceNumber(claim.getInsuranceNumber());
		claimEntryInput.setTotalClaimAmount(-100d);
		Mockito.when(insuranceRepository.findById(claim.getInsuranceNumber())).thenReturn(Optional.of(insurance));
		ClaimEntryOutput actual = claimServiceImpl.claimEntry(claimEntryInput);
		Assert.assertEquals(HttpStatus.OK.value(), actual.getStatusCode().intValue());

	}

	@Test
	public void testClaimEntryPositive() throws ClaimException, AilmentNotFoundException  {
		claimEntryInput.setInsuranceNumber(claim.getInsuranceNumber());
		Mockito.when(insuranceRepository.findById(claim.getInsuranceNumber())).thenReturn(Optional.of(insurance));
		Mockito.when(ailmentRepository.findByNatureOfAilment(claimEntryInput.getAilmentNature()))
				.thenReturn(Optional.of(ailment));
		ClaimEntryOutput actual = claimServiceImpl.claimEntry(claimEntryInput);
		Assert.assertEquals(HttpStatus.OK.value(), actual.getStatusCode().intValue());

	}
	@Test
	public void testHospitalExist() throws ClaimException, AilmentNotFoundException  {
		claimEntryInput.setInsuranceNumber(claim.getInsuranceNumber());
		Mockito.when(insuranceRepository.findById(claim.getInsuranceNumber())).thenReturn(Optional.of(insurance));
		Mockito.when(ailmentRepository.findByNatureOfAilment(claimEntryInput.getAilmentNature()))
				.thenReturn(Optional.of(ailment));
		Mockito.when(hospitalDetailRepository.findByHospitalName(Mockito.any())).thenReturn(Optional.empty());	
		ClaimEntryOutput actual = claimServiceImpl.claimEntry(claimEntryInput);
		Assert.assertEquals(HttpStatus.OK.value(), actual.getStatusCode().intValue());

	}
	@Test
	public void testHospitalOther() throws ClaimException, AilmentNotFoundException{
		claimEntryInput.setInsuranceNumber(claim.getInsuranceNumber());
		Mockito.when(insuranceRepository.findById(claim.getInsuranceNumber())).thenReturn(Optional.of(insurance));
		Mockito.when(ailmentRepository.findByNatureOfAilment(claimEntryInput.getAilmentNature()))
				.thenReturn(Optional.of(ailment));
		Mockito.when(hospitalDetailRepository.findByHospitalName(Mockito.any())).thenReturn(Optional.of(hospitalDetail));	
		ClaimEntryOutput actual = claimServiceImpl.claimEntry(claimEntryInput);
		Assert.assertEquals(HttpStatus.OK.value(), actual.getStatusCode().intValue());

	}
}
