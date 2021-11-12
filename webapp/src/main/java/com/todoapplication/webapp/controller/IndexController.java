package com.todoapplication.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
	
	
	@GetMapping("/detail/{id}")
	public RedirectView detailTaskById(@PathVariable Long id, final RedirectAttributes redirectAttributes) {
		final RedirectView redirectView = new RedirectView("/detail", true);
		final Task task = taskService.getTaskById(id);
		redirectAttributes.addFlashAttribute("task", task);
		return redirectView;
	}
	
	@GetMapping("/detail")
	public String detailTask(Model model, @ModelAttribute("task") final Task task ) {
		System.out.println(task);
		model.addAttribute("mytask", task);
		return "detail";
	}
	
	@GetMapping("/finishtask/{id}")
    public RedirectView updateStateTask(@PathVariable Long id, Model model) {
		taskService.updateStateTask(id);
		RedirectView  redirectView = new RedirectView();
		redirectView.setUrl("/");
		return redirectView;
    }
}