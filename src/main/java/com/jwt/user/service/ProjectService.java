package com.jwt.user.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.jwt.user.dto.ProjectRequest;
import com.jwt.user.entity.Organization;
import com.jwt.user.entity.Project;
import com.jwt.user.entity.Task;
import com.jwt.user.repository.OrganizationRepository;
import com.jwt.user.repository.ProjectRepository;
import com.jwt.user.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private TaskRepository taskRepository;

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

	public Project createProject(int id, ProjectRequest projectRequest) {
		Organization organization = organizationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("organistion not found with id:" + id));

		if (projectRequest.getProjectName() == null || projectRequest.getProjectName().isEmpty()) {
			throw new RuntimeException("Project name must be provided.");
		}
		Optional<Project> existingProject = projectRepository.findByProjectName(projectRequest.getProjectName());
		if (existingProject.isPresent()) {
			throw new RuntimeException("Project with name '" + projectRequest.getProjectName() + "' already exists.");
		}
		Project project = new Project();

		project.setOrganization(organization);
		project.setProjectName(projectRequest.getProjectName());
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

			Project project = optionalProject.get();

			List<Task> byProject = taskRepository.findByProject(project);
			for (Task task : byProject) {
				taskRepository.delete(task);
			}
			projectRepository.deleteById(id);
		} else {

			throw new RuntimeException("Project not found with id: " + id);
		}
	}
}
