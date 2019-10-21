package com.hcl.insuranceclaimsystem.dto;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimApproveRequest {
  @NotEmpty
  private Integer claimId;
  @NotEmpty
  private String claimStatus;
  @NotEmpty
  private String comments;
}
