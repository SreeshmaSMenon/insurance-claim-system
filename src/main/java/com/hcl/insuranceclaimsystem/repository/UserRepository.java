package com.hcl.insuranceclaimsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcl.insuranceclaimsystem.entity.User;
/**
 * Repository for User entity operations.
 * @author Sreeshma S Menon
 * @since 2019/10/21
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);
	Optional<User> findByEmailAndPassword(String email, String password);
	@Query("Select r.roleName FROM Role r JOIN User u  ON r.roleId=u.roleId AND  u.userId = :userId")
	Optional<String> getUserRole(Integer userId);
	

}
