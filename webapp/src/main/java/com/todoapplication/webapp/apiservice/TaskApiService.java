package com.todoapplication.webapp.apiservice;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
	public ArrayList<String> saveNewTask(Task task) {
		ArrayList<String> error = checkTaskParametres(task);
		if(error.isEmpty()) {			
			taskService.saveNewTask(task);
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

	/**
	 * get all task.
	 * @return
	 */
	public Iterable<Task> getAllTask() {
		return taskService.getAllTask();
	}

	/**
	 * get task by id in parm
	 * @param id
	 * @return
	 */
	public Task getTaskById(Long id) {
		return taskService.getTaskById(id);
	}

	/**
	 * Update task by id in parm
	 * @param id
	 */
	public void updateStateTask(Long id) {
		taskService.updateStateTask(id);
	}

	/**
	 * Check param values and get order by back-end. 
	 * @param column
	 * @param order
	 * @return
	 */
	public Iterable<Task> orderBy(String column, String order) {
		boolean checkParamColumn = checkParamColumn(column);
		boolean checkParamOrder = checkParamOrder(order);
		if(!checkParamColumn && !checkParamOrder) {
			final String msg = "Parameters are incorrect.";
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
		}
		return taskService.getColumnOrderBy(column,order);
	}

	/**
	 * Check column value title or createdAt. 
	 * @param order
	 * @return
	 */
	private boolean checkParamColumn(String column) {
		boolean checkParam = false;
		if("createdAt".equals(column)||"title".equals(column)) {
			checkParam = true;
		}
		return checkParam;
	}

	/**
	 * Check order value desc or asc. 
	 * @param order
	 * @return
	 */
	private boolean checkParamOrder(String order) {
		boolean checkParam = false;
		if("desc".equals(order)||"asc".equals(order)) {
			checkParam = true;
		}
		return checkParam;
	}
	
	
	
}
