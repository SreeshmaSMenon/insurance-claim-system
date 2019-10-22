package com.hcl.insuranceclaimsystem.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AilmentResponse {
	private Integer statusCode;
	private String statusMessage;
	private List<AilmentData> ailmentList;
	
}
