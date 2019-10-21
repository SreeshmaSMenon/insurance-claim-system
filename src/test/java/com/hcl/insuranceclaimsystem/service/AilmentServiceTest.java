package com.hcl.insuranceclaimsystem.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.hcl.insuranceclaimsystem.dto.AilmentData;
import com.hcl.insuranceclaimsystem.entity.AilmentDetail;
import com.hcl.insuranceclaimsystem.repository.AilmentRepository;

@RunWith(MockitoJUnitRunner.class)
public class AilmentServiceTest {
	@Mock
	AilmentRepository ailmentRepository;
	@InjectMocks
	AilmentServiceImpl ailmentServiceImpl;
	List<AilmentDetail> ailmentList;

	@Before
	public void setup() {
		ailmentList = new ArrayList<>();
		AilmentDetail ailmentDetail = new AilmentDetail();
		ailmentDetail.setAilmentDetailId(1);
		ailmentDetail.setNatureOfAilment("dengue");
		ailmentDetail.setEligibleAmount(100000.0);
		ailmentList.add(ailmentDetail);
	}

	@Test
	public void testGetAllAilment() {
		Mockito.when(ailmentRepository.findAll()).thenReturn(ailmentList);
		Optional<List<AilmentData>> ailments = ailmentServiceImpl.getAllAilment();
		assertNotNull(ailments);
	}

}
