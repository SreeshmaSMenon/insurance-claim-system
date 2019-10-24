package com.hcl.insuranceclaimsystem.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hcl.insuranceclaimsystem.entity.Ailment;
/**
 * Repository for generic CRUD operations for Ailment.
 * @author Sreeshma S Menon
 */
@Repository
public interface AilmentRepository extends JpaRepository<Ailment, Integer> {
	/**
	 * Retrieves an Ailment entity by ailment name.
	 *
	 * @param ailment which is the nature of ailment by which entity will retrieve.
	 * @return the entity with the given ailment name or {@literal Optional#empty()} if none found
	 */
	public Optional<Ailment> findByNatureOfAilment(String ailment);

}
