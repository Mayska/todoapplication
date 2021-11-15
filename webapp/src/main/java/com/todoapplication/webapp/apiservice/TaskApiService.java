package com.todoapplication.webapp.apiservice;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoapplication.webapp.model.Task;
import com.todoapplication.webapp.service.TaskService;

/**
 * 
 * Check Task parameters from controller
 * @author pc
 *
 */
@Service
public class TaskApiService {
	
	
	
	
	@Autowired
	private TaskService taskService;

	/**
	 * Save new task
	 * @param task
	 * @return
	 */
	public ArrayList<String> saveFrom(Task task) {
		ArrayList<String> error = checkTaskParametres(task);
		if(error.isEmpty()) {			
			taskService.saveFrom(task);
		}
		return error;
	}

	/**
	 * Check all parameters in new Task before save.
	 * @param task
	 * @return
	 */
	private ArrayList<String> checkTaskParametres(Task task) {
		ArrayList<String> listErreur = new ArrayList<String>();
		if(task.getTitle().isEmpty()) {
			listErreur.add("Title is empty");
		}
		if(task.getTitle().length() > 50) {
			listErreur.add("Title too long (50)");
		}
		if(task.getTitle().length() > 250) {
			listErreur.add("Decription too long (250)");
		}
		return listErreur;
	}
	
	
	
}
