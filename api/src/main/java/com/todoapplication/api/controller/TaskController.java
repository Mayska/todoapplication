package com.todoapplication.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.todoapplication.api.apiservice.TaskApiService;
import com.todoapplication.api.model.Task;

import lombok.extern.log4j.Log4j2;

/**
 * @author pc
 *
 */
@Log4j2
@RestController
public class TaskController {
	
	@Autowired
	private TaskApiService taskApiService;
	
	/**
	 * Get all task in db
	 * @return
	 */
	@GetMapping("/alltask")
	@ResponseBody
	public Iterable<Task> getAllTaskList() {
		Iterable<Task> allTask = taskApiService.getAllTask();
		return allTask;
	}
	
	/**
	 * Update state task finish or todo
	 * @param task id
	 * @return
	 */
	@GetMapping("/finishtask/{id}")
	public void updateStateTask(@PathVariable("id") final Long id) {
		taskApiService.updateStateTask(id);
	}
	
	/**
	 * Find task by id
	 * @param task id
	 * @return
	 */
	@GetMapping("/findtask/{id}")
	@ResponseBody
	public Task getTaskById(@PathVariable("id") final Long id) {
		return taskApiService.getTaskById(id);
	}
	

	/**
	 * Submit form and create new task.
	 * @return
	 */
	@PostMapping("/newtask")
	public Task submitNewTask(@RequestBody Task task, Model model) {
		return taskApiService.createNewTask(task);
	}
	
	
	/**
	 * Update state task finish or todo
	 * @param task id
	 * @return 
	 * @return
	 */
	@GetMapping("/orderby")
	@ResponseBody
	public Iterable<Task> orderBy(@RequestParam("column") String column, @RequestParam("order") String order) {
		return taskApiService.getOrderByParam(column, order);
	}
		
}
