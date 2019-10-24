package com.hcl.insuranceclaimsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.insuranceclaimsystem.entity.Role;

/**
 * Repository for generic CRUD operations for Role.
 * @author Sairam
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
