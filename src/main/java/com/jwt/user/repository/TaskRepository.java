package com.jwt.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwt.user.entity.Project;
import com.jwt.user.entity.Task;
import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	
	List<Task> findByProject(Project project);
}
