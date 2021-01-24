package com.cybertek.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cybertek.entity.Role;
import com.cybertek.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByusername(String username);
	
	List<User> findAllByRole(Role role);

	
	//this is trainng purpose , above one must use in prod
	@Query(value = "Select * from Users where role_id = 2", nativeQuery = true)
	List<User> listManagers();

}
