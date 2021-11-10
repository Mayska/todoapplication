package com.todoapplication.api.apiservice;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoapplication.api.constant.TaskConstant;
import com.todoapplication.api.model.Task;
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
	
	public Iterable<Task> getAllTask() {
		Optional<Iterable<Task>> optionalAlltask = Optional.ofNullable(taskService.getAllTask());
		checkObjectPresent(optionalAlltask);
		return optionalAlltask.get();
	}

	private void checkObjectPresent(Optional<Iterable<Task>> ofNullable) {
		if(!ofNullable.isPresent()) {
			logger.info(TaskConstant.ERROR_EXTRACT);
		}
	}
	
	

}
