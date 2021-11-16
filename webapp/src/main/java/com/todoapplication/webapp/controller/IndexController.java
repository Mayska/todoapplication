package com.todoapplication.webapp.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.todoapplication.webapp.apiservice.TaskApiService;
import com.todoapplication.webapp.model.Task;


/**
 * My only controller. 
 * @author pc
 *
 */
@Controller
public class IndexController {
	
	@Autowired
	private TaskApiService taskApiService;

	/**
	 * Get index page and displays all tasks.
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String index(final Model model) {
		Iterable<Task> taskList = taskApiService.getAllTask();
		model.addAttribute("taskList", taskList);
		return "index";
	}
	
	
	/**
	 * Displays task by id to url: '/detail'.
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@GetMapping("/detail/{id}")
	public RedirectView detailTaskById(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
		final RedirectView redirectView = new RedirectView("/detail", true);
		final Task task = taskApiService.getTaskById(id);
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
		taskApiService.updateStateTask(id);
		RedirectView  redirectView = new RedirectView();
		redirectView.setUrl("/");
		return redirectView;
    }
	
	/**
	 * Displays the form or data errors
	 * @param model
	 * @param errorlist
	 * @return
	 */
	@GetMapping("/formtask")
	public String formNewTask(Model model, @ModelAttribute("errorlist") ArrayList<String> errorlist) {
		model.addAttribute("formtask", "formtask");
		model.addAttribute("newtask", new Task());
		model.addAttribute("errorlist", errorlist);
		return "index";
    }
	
	/**
	 * Checks and validates the form for the creation of a new task.
	 * @param task
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/sumbitform")
	public RedirectView sumbitForm(@ModelAttribute Task task, Model model,final RedirectAttributes redirectAttributes) {
		ArrayList<String> error = taskApiService.saveNewTask(task);
		final RedirectView redirectView = new RedirectView();
		if(!error.isEmpty()) {
			redirectView.setUrl("/formtask");
			redirectAttributes.addFlashAttribute("errorlist", error);
		}else {			
			redirectView.setUrl("/");
		}
		return redirectView;
    }
	
	
	/**
	 * Change the display order of the task table.
	 * @param column
	 * @param order
	 * @param model
	 * @return
	 */
	@GetMapping("/orderby")
	public String orderBy(@RequestParam("column") String column, @RequestParam("order") String order, final Model model) {
		Iterable<Task> taskList = taskApiService.orderBy(column, order);
		model.addAttribute("taskList", taskList);
		return "index";
    }
}