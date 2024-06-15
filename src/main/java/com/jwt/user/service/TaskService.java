package com.jwt.user.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.jwt.user.dto.TaskRequest;
import com.jwt.user.entity.Project;
import com.jwt.user.entity.Task;
import com.jwt.user.repository.ProjectRepository;
import com.jwt.user.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private ProjectRepository projectRepository;

	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	public Task getTaskById(Long id) {
		Optional<Task> optionalTask = taskRepository.findById(id);
		if (optionalTask.isPresent()) {
			return optionalTask.get();
		} else {
			throw new RuntimeException("Task not found with id: " + id);
		}
	}

	public Task createTask(TaskRequest taskRequest) {
		Project project = projectRepository.findById(taskRequest.getProjectId()).orElseThrow(
				() -> new RuntimeException("Project with id " + taskRequest.getProjectId() + " not found"));

		Task task = new Task();
		task.setName(taskRequest.getName());
		task.setProject(project); 

		return taskRepository.save(task);
	}

	public Task updateTask(Long id, Task updatedTask) {
		Optional<Task> optionalTask = taskRepository.findById(id);
		if (optionalTask.isPresent()) {
			updatedTask.setId(id);
			return taskRepository.save(updatedTask);
		} else {
			throw new RuntimeException("Task not found with id: " + id);
		}
	}

	public void deleteTask(Long id) {
		Optional<Task> optionalTask = taskRepository.findById(id);
		if (optionalTask.isPresent()) {
			taskRepository.deleteById(id);
		} else {
			throw new RuntimeException("Task not found with id: " + id);
		}
	}
}
