package com.jwt.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwt.user.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
