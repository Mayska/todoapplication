package com.todoapplication.api.apiservice;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
	 * Checking the id
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

	public Task createNewTask(Task task) {
		final Task checkParm = checkParm(task);
		return taskService.createNewTask(checkParm);
	}

	/**
	 * Checking the title and creating the parameters.
	 * @param task
	 * @return
	 */
	private Task checkParm(Task task) {
		if(task.getTitle().isEmpty()) {
			final String msg = TaskConstant.ERROR_TASK_TITLE_EMPTY;
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
		}
		if(task.getDescription().isEmpty()) {
			task.setDescription("");
		}
		task.setState(true);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		OffsetDateTime now = OffsetDateTime.now();
		task.setCreatedAt(now.format(formatter));
		// task.setCreatedAt(new Date());
		return task;
	}
}
