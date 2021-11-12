package com.todoapplication.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

import com.todoapplication.webapp.model.Task;
import com.todoapplication.webapp.service.TaskService;


@Controller
public class IndexController {
	
	@Autowired
	private TaskService taskService;

	@GetMapping("/")
	public String index(Model model) {
		Iterable<Task> taskList = taskService.getAllTask();
		model.addAttribute("newtask", new Task());
		model.addAttribute("taskList", taskList);
		return "index";
	}
	
	@GetMapping("/finishtask/{id}")
    public RedirectView updateStateTask(@PathVariable Long id, Model model) {
		taskService.updateStateTask(id);
		RedirectView  redirectView = new RedirectView();
		redirectView.setUrl("/");
		return redirectView;
    }
}