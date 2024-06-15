package com.jwt.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwt.user.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

	Optional<Project> findByProjectName(String projectName);
}
