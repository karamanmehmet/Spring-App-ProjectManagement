package com.cybertek.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cybertek.entity.Project;
import com.cybertek.entity.User;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	Project getByCode(String code);

	List<Project> findAllByManager(User manager);
	
	@Modifying
	@Transactional
	@Query("Delete from Project where code = :code ")
	boolean deleteProject(String code);
}
