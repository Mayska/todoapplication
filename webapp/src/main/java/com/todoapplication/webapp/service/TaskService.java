package com.todoapplication.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoapplication.webapp.model.Task;
import com.todoapplication.webapp.repository.TaskProxy;

/**
 * Service
 * @author pc
 *
 */
@Service
public class TaskService {

	@Autowired
	private TaskProxy taskProxy;

	/**
	 * Asks all task at back-end.
	 * @return
	 */
	public Iterable<Task> getAllTask() {
		final Iterable<Task> allTask = taskProxy.getAllTask();
		return allTask;
	}

	/**
	 * Asks update the status of the task by id.
	 * @param id
	 */
	public void updateStateTask(Long id) {
		taskProxy.updateStateTask(id);
	}

	/**
	 * Asks task by id.
	 * @param id
	 * @return
	 */
	public Task getTaskById(Long id) {
		return taskProxy.getTaskById(id);
		
	}

	public void saveNewTask(Task task) {
		taskProxy.saveForm(task);
	}

}
