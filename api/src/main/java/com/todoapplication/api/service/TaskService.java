package com.todoapplication.api.service;

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
		String value = "state";
		final Direction desc = Sort.Direction.DESC;
		final Sort sortBy = sortBy(desc, value);
		final Iterable<Task> findAll = taskRepository.findAll(sortBy);
		logger.info(TaskConstant.GET_ALL_TASK);
		return findAll;
	}
	
	/**
	 * Update state 
	 * @param task
	 */
	public void updateStateTask(Task task) {
		if(task.isState()) {
			task.setState(false);
		}else {
			task.setState(true);
		}
		final Task saveTask = taskRepository.save(task);
		logger.info(TaskConstant.UPDATE_TASK_STATE + saveTask.getId());
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

	public Task getTaskById(Task task) {
		return task;
	}
	

}
