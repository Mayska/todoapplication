package com.todoapplication.api.apiservice;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.todoapplication.api.constant.TaskConstant;
import com.todoapplication.api.model.Task;
import com.todoapplication.api.repository.TaskRepository;
import com.todoapplication.api.service.TaskService;

/**
 * Checks the integrity of the data between the controller and the service.
 * @author pc
 *
 */
@Service
public class TaskApiService {
	
	private static final Logger logger = LogManager.getLogger(TaskApiService.class);

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TaskRepository taskRepository;
	
	/**
	 * Get all Task in DB
	 * @return
	 */
	public Iterable<Task> getAllTask() {
		final Iterable<Task> allTask = taskService.getAllTask();
		return allTask;
	}

	/**
	 * Checking the id
	 * @param id
	 */
	public void updateStateTask(Long id) {
		final Task TaskExist = checkTaskIdExist(id);
		taskService.updateStateTask(TaskExist);
	}

	/**
	 * Checking the id and get Task object or BAD_REQUEST
	 * @param id
	 * @return
	 */
	private Task checkTaskIdExist(Long id) {
		final Optional<Task> optionalTask = taskRepository.findById(id);
		if(!optionalTask.isPresent()) {
			String msg = TaskConstant.ERROR_TASK_ID + id;
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
		}
		return optionalTask.get();
	}

}
