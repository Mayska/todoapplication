package com.todoapplication.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.todoapplication.webapp.model.Task;
import com.todoapplication.webapp.service.TaskService;

import lombok.Data;

@Data
@Controller
public class IndexController {
	
	@Autowired
	private TaskService taskService;

	@GetMapping("/")
	public String index(Model model) {
		Iterable<Task> taskList = taskService.getAllTask();
		model.addAttribute("taskList", taskList);
		return "index";
	}
}