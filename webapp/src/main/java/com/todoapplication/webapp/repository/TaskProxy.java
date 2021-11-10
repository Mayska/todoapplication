package com.todoapplication.webapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.todoapplication.webapp.CustomProperties;
import com.todoapplication.webapp.model.Task;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TaskProxy {

	@Autowired
	private CustomProperties props;

	public Iterable<Task> getAllTask() {
		String baseApiUrl = props.getApiUrl();
		String getAllTaskUrl = baseApiUrl + "/alltask";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Iterable<Task>> response = restTemplate.exchange(getAllTaskUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<Iterable<Task>>() {
				});
		return response.getBody();
	}

}
