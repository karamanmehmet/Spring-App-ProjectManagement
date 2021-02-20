package com.cybertek.repository;

import java.util.List;

import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.cybertek.entity.Project;
import com.cybertek.entity.Task;
import com.cybertek.entity.User;
import com.cybertek.util.Status;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findAllByUser(User user);

	List<Task> findAllByProject(Project project);

	List<Task> findAllByUserAndProject(User user, Project project);

	@Transactional
	void deleteByProject(Project project);

	@Procedure(name = "totalNonCompletedTasks")
	int totalNonCompletedTasks(String projectCode);

	@Procedure(name = "totalCompletedTasks")
	int totalCompletedTasks(String projectCode);

	List<Task> findAllByStatusNQ(Status status, User user);

	List<Task> findAllByStatusIsNotAndUser(Status status, User user);

}
