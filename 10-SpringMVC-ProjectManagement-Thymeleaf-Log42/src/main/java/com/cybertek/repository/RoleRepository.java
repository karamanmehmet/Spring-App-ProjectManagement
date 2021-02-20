package com.cybertek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cybertek.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByDescription(String description);

}
