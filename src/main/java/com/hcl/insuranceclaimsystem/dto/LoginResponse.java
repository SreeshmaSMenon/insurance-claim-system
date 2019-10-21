package com.hcl.insuranceclaimsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
  private Integer statusCode;
  private String message;
  private Integer userId;

  
  
}
