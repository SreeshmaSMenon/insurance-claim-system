package com.hcl.insuranceclaimsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClaimEntryOutput {

	private String message;
	private Integer claimId;
	private Integer statusCode;

}
