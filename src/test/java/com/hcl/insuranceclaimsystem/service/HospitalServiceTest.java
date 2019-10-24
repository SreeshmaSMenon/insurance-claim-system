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

import com.hcl.insuranceclaimsystem.dto.HospitalDetail;
import com.hcl.insuranceclaimsystem.entity.Hospital;
import com.hcl.insuranceclaimsystem.repository.HospitalDetailRepository;

@RunWith(MockitoJUnitRunner.class)
public class HospitalServiceTest {
	@Mock
	HospitalDetailRepository hospitalDetailRepository;
	@InjectMocks
	HospitalServiceImpl hospitalService;
	List<Hospital> details;
	Hospital hospitalDetail;
	@Before
	public void setup() {
		hospitalDetail = new Hospital();
		hospitalDetail.setHospitalId(1);
		details = new ArrayList<>();
		details.add(hospitalDetail);
	}
	@Test
	public void testGetAllHospitalDetails() {
		Mockito.when(hospitalDetailRepository.findAll()).thenReturn(details);
		Optional<List<HospitalDetail>> hospitalDetailsList = hospitalService.getAllHospitals();
		Assert.assertNotNull(hospitalDetailsList);
	}
	
}
