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
import com.hcl.insuranceclaimsystem.dto.HospitalDetail;
import com.hcl.insuranceclaimsystem.exception.HospitalNotFoundException;
import com.hcl.insuranceclaimsystem.service.HospitalService;

@RunWith(MockitoJUnitRunner.class)
public class HospitalControllerTest {
	@Mock
	HospitalService hospitalService;
	@InjectMocks
	HospitalController hospitalController;
	HospitalDetail hospitalDetail;
	List<HospitalDetail> hospitalDetails;
	@Before
	public void setup() {
		hospitalDetail = new HospitalDetail();
		hospitalDetails = new ArrayList<>();
		hospitalDetails.add(hospitalDetail);
	}
	@Test
	public void testGetAllHospitalDetails() throws HospitalNotFoundException {
		Mockito.when(hospitalService.getAllHospitals()).thenReturn(Optional.of(hospitalDetails));
		ResponseEntity<List<HospitalDetail>> actual = hospitalController.getAllHospitals();
		ResponseEntity<List<HospitalDetail>> expected = new ResponseEntity<>(hospitalDetails, HttpStatus.OK);
		Assert.assertEquals(expected.getStatusCodeValue(), actual.getStatusCodeValue());
	}

	@Test(expected = HospitalNotFoundException.class)
	public void testExpectedCommonException() throws HospitalNotFoundException {
		Mockito.when(hospitalService.getAllHospitals()).thenReturn(Optional.empty());
		ResponseEntity<List<HospitalDetail>> actual = hospitalController.getAllHospitals();
		ResponseEntity<List<HospitalDetail>> expected = new ResponseEntity<>(hospitalDetails, HttpStatus.OK);
		Assert.assertEquals(expected.getStatusCodeValue(), actual.getStatusCodeValue());
	}
}
