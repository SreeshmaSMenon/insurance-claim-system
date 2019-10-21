package com.hcl.insuranceclaimsystem.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

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
import com.hcl.insuranceclaimsystem.dto.LoginRequest;
import com.hcl.insuranceclaimsystem.dto.LoginResponse;
import com.hcl.insuranceclaimsystem.entity.User;
import com.hcl.insuranceclaimsystem.exception.UserException;
import com.hcl.insuranceclaimsystem.exception.UserNotFoundException;
import com.hcl.insuranceclaimsystem.service.LoginService;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

   @Mock
   LoginService loginService;  
   @InjectMocks
   LoginController loginController;   
   BindingResult bindingResult;
   User user;
   LoginRequest loginRequest;
   FieldError fieldError;
   
   @Before
   public void setup() {
	   bindingResult = mock(BindingResult.class);
	   user=new User();
	   user.setUserId(1);
	   user.setEmail("sree@gmail.com");
	   user.setPassword("sredsdsf");
	   loginRequest=new LoginRequest();
	   loginRequest.setEmail("sree@gmail.com");
	   loginRequest.setPassword("sredsdsf");
	   fieldError=new FieldError("loginRequest", "userName", "Must Not empty");
   }
   
   @Test
   public void testLogin() throws UserException, UserNotFoundException {
	   Mockito.when(bindingResult.hasErrors()).thenReturn(false);
	   Mockito.when(loginService.getUser(Mockito.any())).thenReturn(Optional.of(user));
	   ResponseEntity<LoginResponse> loginResponse=loginController.login(loginRequest, bindingResult);
	   assertNotNull(loginResponse);
   }
   @Test(expected=UserException.class)
   public void testLoginBindException() throws UserException, UserNotFoundException {
	   Mockito.when(bindingResult.hasErrors()).thenReturn(true);
	   Mockito.when(bindingResult.getFieldError()).thenReturn(fieldError);
	   ResponseEntity<LoginResponse> loginResponse=loginController.login(loginRequest, bindingResult);
	   assertNotNull(loginResponse);
   }
   @Test(expected = UserNotFoundException.class)
   public void testUsetNotFoundException() throws UserException, UserNotFoundException {
	   Mockito.when(bindingResult.hasErrors()).thenReturn(false);
	   Mockito.when(loginService.getUser(Mockito.any())).thenReturn(Optional.empty());
	   ResponseEntity<LoginResponse> loginResponse=loginController.login(loginRequest, bindingResult);
	   assertNotNull(loginResponse);
   }

}
