package com.todoapplication.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.todoapplication.api.apiservice.TaskApiService;
import com.todoapplication.api.constant.TaskConstant;
import com.todoapplication.api.model.Task;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * @author pc
 *
 */
@Log4j2
@RestController
public class TaskController {
	
	private static final Logger logger = LogManager.getLogger(TaskController.class);
	
	@Autowired
	private TaskApiService taskApiService;
	
	@GetMapping("/alltask")
	@ResponseBody
	public Iterable<Task> getAllTaskList() {
		Iterable<Task> allTask = taskApiService.getAllTask();
		return allTask;
	}

		
}
