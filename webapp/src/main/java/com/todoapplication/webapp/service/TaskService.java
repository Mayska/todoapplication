package com.todoapplication.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoapplication.webapp.model.Task;
import com.todoapplication.webapp.repository.TaskProxy;

import lombok.Data;

@Service
public class TaskService {

	@Autowired
	private TaskProxy taskProxy;

	public Iterable<Task> getAllTask() {
		final Iterable<Task> allTask = taskProxy.getAllTask();
		return allTask;
	}

	public void updateStateTask(Long id) {
		taskProxy.updateStateTask(id);
	}

	public Task getTaskById(Long id) {
		return taskProxy.getTaskById(id);
		
	}
	
	

}
