package com.todoapplication.api.service;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.todoapplication.api.constant.TaskConstant;
import com.todoapplication.api.model.Task;
import com.todoapplication.api.repository.TaskRepository;


/**
 * Business data processing.
 * @author pc
 *
 */
@Service
public class TaskService {
	
	@Autowired
	private TaskRepository taskRepository;
	
	private static final Logger logger = LogManager.getLogger(TaskService.class);
	
	/**
	 * Get all task in DB.
	 * @return
	 */
	public Iterable<Task> getAllTask() {
		String state = "state";
		String date = "createdAt";
		final Direction desc = Sort.Direction.DESC;
		final Sort sortByState = sortBy(desc, state);
		final Sort sortByDate = sortBy(desc, date);
		final Iterable<Task> findAll = taskRepository.findAll(sortByState.and(sortByDate));
		logger.info(TaskConstant.GET_ALL_TASK);
		return findAll;
	}
	
	/**
	 * Update state 
	 * @param task
	 * @return 
	 */
	public Task updateStateTask(Task task) {
		final Task saveTask = taskRepository.save(task);
		logger.info(TaskConstant.UPDATE_TASK_STATE + saveTask.getId());
		return saveTask;
	}
	
	/**
	 * Choice of sorting
	 * @param desc
	 * @param value
	 * @return
	 */
	private Sort sortBy(Direction desc, String value) {
		return Sort.by(desc, value);
	}

	/***
	 * Get task by id in DB.
	 * @param task
	 * @return
	 */
	public Task getTaskById(Task task) {
		logger.info(TaskConstant.GET_TASK_ID + task.getId());
		return task;
	}

	public Task createNewTask(Task task) {
		final Task saveTask = taskRepository.save(task);
		logger.info(TaskConstant.CREATE_TASK + saveTask.getId());
		return saveTask;
	}

	

}
