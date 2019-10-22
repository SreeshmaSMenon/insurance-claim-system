package com.hcl.insuranceclaimsystem.controller;

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
import org.springframework.http.ResponseEntity;
import com.hcl.insuranceclaimsystem.dto.AilmentData;
import com.hcl.insuranceclaimsystem.dto.AilmentResponse;
import com.hcl.insuranceclaimsystem.service.AilmentService;

@RunWith(MockitoJUnitRunner.class)
public class AilmentControllerTest {

	@Mock
	AilmentService ailmentService;
    @InjectMocks
	AilmentController ailmentController;
    List<AilmentData>ailmentList;
    @Before
    public void setup() {
    	ailmentList=new ArrayList<>();
        AilmentData ailmentData=new AilmentData();
        ailmentData.setLabel("dengue");
        ailmentData.setValue("dengue");
        ailmentList.add(ailmentData);
    }
    
    @Test
    public void testGetAllAilment() {
    	Mockito.when(ailmentService.getAllAilment()).thenReturn(Optional.of(ailmentList));
 	   ResponseEntity<AilmentResponse> ailmentResponse=ailmentController.getAllAilments();
 	   assertNotNull(ailmentResponse);
    }
}
