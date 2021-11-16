package com.todoapplication.api.apiservice;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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
	 * Checking the id and get Task object or BAD_REQUEST
	 * @param id
	 * @return
	 */
	private Task checkTaskIdExist(Long id) {
		final Optional<Task> optionalTask = taskRepository.findById(id);
		if(!optionalTask.isPresent()) {
			final String msg = TaskConstant.ERROR_TASK_ID + id;
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
		}
		return optionalTask.get();
	}

	/**
	 * Checking the id
	 * @param id
	 * @return
	 */
	public Task getTaskById(Long id) {
		final Task TaskExist = checkTaskIdExist(id);
		return taskService.getTaskById(TaskExist);
	}

	/**
	 * Checking the id and update Task object.
	 * @param id
	 * @return 
	 */
	public Task updateStateTask(Long id) {
		final Task TaskExist = checkTaskIdExist(id);
		if(TaskExist.isState()) {
			TaskExist.setState(false);
		}else {
			TaskExist.setState(true);
		}
		return taskService.updateStateTask(TaskExist);
	}

	/**
	 * Create new Task if param is valid
	 * @param task
	 * @return
	 */
	public Task createNewTask(Task task) {
		final Task checkParm = checkParm(task);
		return taskService.createNewTask(checkParm);
	}

	/**
	 * Checking the title and creating Task the parameters.
	 * @param task
	 * @return
	 */
	private Task checkParm(Task task) {
		if(task.getTitle().isEmpty()) {
			final String msg = TaskConstant.ERROR_TASK_TITLE_EMPTY;
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
		}
		if(task.getTitle().length() > 50){
			final String msg = TaskConstant.ERROR_TASK_TITLE_LONG;
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
		}
		if(task.getDescription().isEmpty()) {
			task.setDescription(TaskConstant.EMPTY);
		}
		if(task.getDescription().length() > 250){
			final String msg = TaskConstant.ERROR_TASK_DESCRIPTION_LONG;
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
		}
		task.setState(true);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TaskConstant.PATTERNE_DATE);
		OffsetDateTime now = OffsetDateTime.now();
		task.setCreatedAt(now.format(formatter));
		return task;
	}

	/**
	 * Check parameters value and get order by.
	 * @param column
	 * @param order
	 * @return
	 */
	public Iterable<Task> getOrderByParam(String column, String order) {
		boolean valueColumn = checkValueColumn(column);
		boolean valueOrder = checkValueOrder(order);
		if(!valueColumn && !valueOrder) {
			final String msg = TaskConstant.ERROR_PARAM_URL;
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
		}
		return taskService.getColumnOrderBy(column,order);
	}
	
	/**
	 * Check column value createdAt or title
	 * @param column
	 * @return
	 */
	private boolean checkValueColumn(String column) {
		boolean checkValue = false;
		if("createdAt".equals(column)||"title".equals(column)) {
			checkValue = true;
		}
		return checkValue;
	}

	/**
	 * Check order value desc or asc 
	 * @param order
	 * @return
	 */
	private boolean checkValueOrder(String order) {
		boolean checkValue = false;
		if("desc".equals(order)||"asc".equals(order)) {
			checkValue = true;
		}
		return checkValue;
	}

}
