package com.hcl.insuranceclaimsystem.service;

import java.util.List;
import java.util.Optional;
import com.hcl.insuranceclaimsystem.dto.AilmentData;

public interface AilmentService {
 public Optional<List<AilmentData>>getAllAilment();
}
