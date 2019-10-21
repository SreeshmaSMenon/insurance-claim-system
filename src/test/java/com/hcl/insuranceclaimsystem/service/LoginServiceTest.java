package com.hcl.insuranceclaimsystem.service;

import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.hcl.insuranceclaimsystem.dto.LoginRequest;
import com.hcl.insuranceclaimsystem.entity.User;
import com.hcl.insuranceclaimsystem.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {
	@Mock
	UserRepository userRepository;
	@InjectMocks
	LoginServiceImpl loginServiceImpl;
	User user;
	LoginRequest loginRequest;

	@Before
	public void setup() {
		user = new User();
		user.setUserId(1);
		user.setEmail("sree@gmail.com");
		user.setPassword("sree123");
		loginRequest = new LoginRequest();
		loginRequest.setEmail("sree@gmail.com");
		loginRequest.setPassword("sree123");
	}

	@Test
	public void testGetUerByUsernameAndPassword() {
		Mockito.when(userRepository.findByEmailAndPassword(Mockito.any(),Mockito.any())).thenReturn(Optional.of(user));
		Optional<User> user = loginServiceImpl.getUser(loginRequest);
		assertNotNull(user);
	}
}
