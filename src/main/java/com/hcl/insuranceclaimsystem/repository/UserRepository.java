package com.hcl.insuranceclaimsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcl.insuranceclaimsystem.entity.User;
/**
 * Repository for generic CRUD operations for User.
 * @author Sairam
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	/**
	 * Retrieve the User detail based on given email and password. 
	 * @param email which is email of user.
	 * @param password which is the password of user.
	 * @return Optional which contains User entity if found otherwise empty.
	 */
	Optional<User> findByEmailAndPassword(String email, String password);
	/**
	 * Retrieve the User role based on given userId. 
	 * @param userId which is the id of user whose role to be retrieve.
	 * @return Optional which contains role type.
	 */
	@Query("Select r.roleName FROM Role r JOIN User u  ON r.roleId=u.roleId AND  u.userId = :userId")
	Optional<String> getUserRole(Integer userId);
	

}
