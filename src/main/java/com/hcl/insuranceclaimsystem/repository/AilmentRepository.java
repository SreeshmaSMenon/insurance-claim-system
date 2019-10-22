package com.hcl.insuranceclaimsystem.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hcl.insuranceclaimsystem.entity.Ailment;
@Repository
public interface AilmentRepository extends JpaRepository<Ailment, Integer> {
	public Optional<Ailment> findByNatureOfAilment(String ailment);

}
