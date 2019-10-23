package com.hcl.insuranceclaimsystem.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.hcl.insuranceclaimsystem.dto.ClaimApproveRequest;
import com.hcl.insuranceclaimsystem.dto.CommonResponse;
import com.hcl.insuranceclaimsystem.entity.ClaimDetail;
import com.hcl.insuranceclaimsystem.exception.ClaimsNotFoundException;
import com.hcl.insuranceclaimsystem.exception.UserException;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;
import com.hcl.insuranceclaimsystem.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
	@Mock
	UserService userService;
	@InjectMocks
	UserController userController;
	BindingResult bindingResult;
	FieldError fieldError;
	ClaimApproveRequest claimApproveRequest;
	ClaimDetail claimDetail;

	@Before
	public void setup() {
		bindingResult = mock(BindingResult.class);
		fieldError = new FieldError("loginRequest", "claimStatus", "Must Not empty");
		claimApproveRequest = new ClaimApproveRequest();
		claimApproveRequest.setClaimId(1);
		claimApproveRequest.setClaimStatus("APPROVED");
		claimApproveRequest.setComments("Approving");
		claimDetail = new ClaimDetail();
		claimDetail.setApprovalDate(LocalDateTime.now());
		claimDetail.setApprovalStatus("Approved");
		claimDetail.setClaimId(1);
		claimDetail.setComments("Approved by first level");
	}

	@Test
	public void testApproveClaim() throws UserNotFoundException, UserException, ClaimsNotFoundException {
		Mockito.when(bindingResult.hasErrors()).thenReturn(false);
		Mockito.when(userService.approveClaim(Mockito.anyInt(), Mockito.any())).thenReturn(Optional.of(claimDetail));
		ResponseEntity<CommonResponse> commonResponse = userController.approveClaim(claimApproveRequest, 1,
				bindingResult);
		assertNotNull(commonResponse);
	}

	@Test(expected = UserException.class)
	public void testUserException() throws UserNotFoundException, UserException, ClaimsNotFoundException {
		Mockito.when(bindingResult.hasErrors()).thenReturn(true);
		Mockito.when(bindingResult.getFieldError()).thenReturn(fieldError);
		ResponseEntity<CommonResponse> commonResponse = userController.approveClaim(claimApproveRequest, 1,
				bindingResult);
		assertNotNull(commonResponse);
	}
}
