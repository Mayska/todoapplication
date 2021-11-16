package com.todoapplication.webapp.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.todoapplication.webapp.CustomProperties;
import com.todoapplication.webapp.model.Task;

import lombok.extern.slf4j.Slf4j;

/**
 * Call api back-end 
 * @author pc
 *
 */
@Slf4j
@Component
public class TaskProxy {

	@Autowired
	private CustomProperties props;
	
	private static final Logger logger = LogManager.getLogger(TaskProxy.class);

	/**
	 * Send request getAllTaskList().
	 * @return
	 */
	public Iterable<Task> getAllTask() {
		String baseApiUrl = props.getApiUrl();
		String getAllTaskUrl = baseApiUrl + "/alltask";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Iterable<Task>> response = restTemplate.exchange(getAllTaskUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<Iterable<Task>>() {
				});
		logger.info("Call api back-end getAllTask()");
		return response.getBody();
	}

	/**
	 * Send request updateStateTask(id).
	 * @param id
	 */
	public void updateStateTask(Long id) {
		String baseApiUrl = props.getApiUrl();
		String finishTaskUrl = baseApiUrl + "/finishtask/" + id;
		RestTemplate restTemplate = new RestTemplate();
		logger.info("Call api back-end updateStateTask() [ID] : {0}",  id);
		restTemplate.exchange(finishTaskUrl, HttpMethod.GET, null, void.class);
	}

	/**
	 * Send request getTaskById(id).
	 * @param id
	 * @return
	 */
	public Task getTaskById(Long id) {
		String baseApiUrl = props.getApiUrl();
		String getTaskByIdUrl = baseApiUrl + "/findtask/" + id;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Task> response = restTemplate.exchange(getTaskByIdUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<Task>() {
				});
		logger.info("Call api back-end getTaskById() [ID] : {0}",  id);
		return response.getBody();
		
	}

	/**
	 * Send request submitNewTask(task). 
	 * @param task
	 */
	public void saveForm(Task task) {
		String baseApiUrl = props.getApiUrl();
		String saveTaskUrl = baseApiUrl + "/newtask";
		RestTemplate restTemplate = new RestTemplate();
		final HttpEntity<Task> httpEntity = new HttpEntity<Task>(task);
		logger.info("Call api back-end saveForm");
		restTemplate.exchange(saveTaskUrl, HttpMethod.POST, httpEntity, Task.class);
		
	}

	/**
	 * Send request orderBy(column, order)
	 * @param column
	 * @param order
	 * @return
	 */
	public Iterable<Task> getColumnOrderBy(String column, String order) {
		String baseApiUrl = props.getApiUrl();
		String getAllTaskUrl = baseApiUrl + "/orderby?column=" + column +"&order=" + order;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Iterable<Task>> response = restTemplate.exchange(getAllTaskUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<Iterable<Task>>() {
				});
		logger.info("Call api back-end getColumnOrderBy()");
		return response.getBody();
		
	}

}
