package com.todoapplication.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.todoapplication.webapp.apiservice.TaskApiService;
import com.todoapplication.webapp.model.Task;
import com.todoapplication.webapp.service.TaskService;


/**
 * My only controller. 
 * @author pc
 *
 */
@Controller
public class IndexController {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TaskApiService taskApiService;

	/**
	 * Get index page and displays all tasks.
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String index(final Model model) {
		Iterable<Task> taskList = taskService.getAllTask();
		model.addAttribute("taskList", taskList);
		return "index";
	}
	
	
	/**
	 * Returns task by id to url: '/detail'.
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@GetMapping("/detail/{id}")
	public RedirectView detailTaskById(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
		final RedirectView redirectView = new RedirectView("/detail", true);
		final Task task = taskService.getTaskById(id);
		redirectAttributes.addFlashAttribute("task", task);
		return redirectView;
	}
	
	/**
	 * Displays task details.
	 * @param model
	 * @param task
	 * @return
	 */
	@GetMapping("/detail")
	public String detailTask(final Model model, @ModelAttribute("task") final Task task ) {
		model.addAttribute("mytask", task);
		return "detail";
	}
	
	/**
	 * Asks back-end to update the status of the task by id and redirect url: '/'.
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/finishtask/{id}")
    public RedirectView updateStateTask(@PathVariable final Long id, final Model model) {
		taskService.updateStateTask(id);
		RedirectView  redirectView = new RedirectView();
		redirectView.setUrl("/");
		return redirectView;
    }
	
	@GetMapping("/formtask")
	public String formNewTask(Model model, @ModelAttribute("errorlist") ArrayList<String> errorlist) {
		model.addAttribute("formtask", "formtask");
		model.addAttribute("newtask", new Task());
		model.addAttribute("errorlist", errorlist);
		return "index";
    }
	
	@PostMapping("/sumbitform")
	public RedirectView sumbitForm(@ModelAttribute Task task, Model model,final RedirectAttributes redirectAttributes) {
		ArrayList<String> error = taskApiService.saveFrom(task);
		final RedirectView redirectView = new RedirectView();
		if(!error.isEmpty()) {
			redirectView.setUrl("/formtask");
			redirectAttributes.addFlashAttribute("errorlist", error);
		}else {			
			redirectView.setUrl("/");
		}
		return redirectView;
    }
}