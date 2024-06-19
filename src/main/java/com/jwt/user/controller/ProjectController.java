package com.jwt.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.user.dto.ProjectRequest;
import com.jwt.user.entity.Project;
import com.jwt.user.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@GetMapping("/getProjectList")
	public ResponseEntity<List<Project>> getAllProjects() {
		List<Project> projects = projectService.getAllProjects();
		return ResponseEntity.ok().body(projects);
	}

	@GetMapping("/get-project/{projectId}")
	public ResponseEntity<Project> getProjectById(@PathVariable("projectId") int projectId) {
		Project project = projectService.getProjectById(projectId);
		return ResponseEntity.ok().body(project);
	}

	@PostMapping("/create-project/{organizationId}")
	public ResponseEntity<Project> createProject(@PathVariable("organizationId") int organizationId, @RequestBody ProjectRequest projectRequest) {
		Project createdProject = projectService.createProject(organizationId,projectRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
	}

	@PutMapping("/update-project/{projectId}")
	public ResponseEntity<Project> updateProject(@PathVariable("projectId") int projectId, @RequestBody Project updatedProject) {
		Project project = projectService.updateProject(projectId, updatedProject);
		return ResponseEntity.ok().body(project);
	}

	@DeleteMapping("delete-project/{projectId}")
	public ResponseEntity<String> deleteProject(@PathVariable("projectId") int projectId) {
		projectService.deleteProject(projectId);
		return ResponseEntity.ok().body("Project is deleted succssfully");
	}
}
