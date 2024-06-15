package com.jwt.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwt.user.entity.Project;
import com.jwt.user.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}

	public Project getProjectById(int id) {
		Optional<Project> optionalProject = projectRepository.findById(id);
		if (optionalProject.isPresent()) {
			return optionalProject.get();
		} else {
			throw new RuntimeException("Project not found with id: " + id);
		}
	}

	public Project createProject(Project project) {
		if (project.getProjectName() == null || project.getProjectName().isEmpty()) {
			throw new IllegalArgumentException("Project name must be provided.");
		}
		Optional<Project> existingProject = projectRepository.findByProjectName(project.getProjectName());
		if (existingProject.isPresent()) {
			throw new RuntimeException("Project with name '" + project.getProjectName() + "' already exists.");
		}

		return projectRepository.save(project);
	}

	public Project updateProject(int id, Project updatedProject) {
		Optional<Project> optionalProject = projectRepository.findById(id);
		if (optionalProject.isPresent()) {
			Project existingProject = optionalProject.get();
			existingProject.setProjectName(updatedProject.getProjectName());
			return projectRepository.save(existingProject);
		} else {
			throw new RuntimeException("Project not found with id: " + id);
		}
	}

	public void deleteProject(int id) {
		Optional<Project> optionalProject = projectRepository.findById(id);
		if (optionalProject.isPresent()) {
			projectRepository.deleteById(id);
		} else {
			throw new RuntimeException("Project not found with id: " + id);
		}
	}
}
