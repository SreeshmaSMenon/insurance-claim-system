package com.hcl.insuranceclaimsystem.service;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import com.hcl.insuranceclaimsystem.dto.ClaimEntryInput;
import com.hcl.insuranceclaimsystem.dto.ClaimEntryOutput;
import com.hcl.insuranceclaimsystem.entity.Claim;
import com.hcl.insuranceclaimsystem.entity.Insurance;
import com.hcl.insuranceclaimsystem.exception.CommonException;
import com.hcl.insuranceclaimsystem.repository.ClaimRepository;
import com.hcl.insuranceclaimsystem.repository.InsuranceRepository;

@RunWith(MockitoJUnitRunner.class)
class ClaimServiceImplTest {

	@Mock
	ClaimRepository claimRepository;
	@Mock
	InsuranceRepository insuranceRepository;
	@InjectMocks
	ClaimServiceImpl claimServiceImpl;

	Insurance insurance;
	Claim claim;
	ClaimEntryInput claimEntryInput;

	@Before
	public void setup() {
		insurance = new Insurance();
		insurance.setAddress("address");
		insurance.setCustomerName("Name");
		insurance.setDob(LocalDate.now());
		insurance.setInsuranceNumber(1);
		insurance.setMobileNumber(999999999L);
		claim = new Claim();
		claim.setClaimId(1);

		claimEntryInput = new ClaimEntryInput();
		claimEntryInput.setAdmissionDate(LocalDate.now());
		claimEntryInput.setAilmentNature("alitname");
		claimEntryInput.setCustomerName("customerName");
		claimEntryInput.setDiagnosis("diagnosis");
		claimEntryInput.setTotalClaimAmount(10000d);
		claimEntryInput.setDischargeDate(LocalDate.now().plusDays(2));
	}

	@Test
	void testClaimEntry() throws CommonException {
		Mockito.when(insuranceRepository.findById(insurance.getInsuranceNumber())).thenReturn(Optional.of(insurance));
		Mockito.when(claimRepository.save(claim)).thenReturn(claim);
		ClaimEntryOutput actual = claimServiceImpl.claimEntry(claimEntryInput);
		Assert.assertEquals(HttpStatus.OK.value(), actual.getStatusCode().intValue());

	}

}
