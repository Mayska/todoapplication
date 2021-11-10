package com.todoapplication.api;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.todoapplication.api.model.Task;
import com.todoapplication.api.repository.TaskRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {
	

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TaskRepository taskRepository; 
	
	private final DateTimeFormatter isoDateTime = DateTimeFormatter.ISO_DATE_TIME;
	
	/**
	 * Test calling the method getAllTask status 200
	 * @throws Exception
	 */
	@Test
	public void TestGetAllTask_TaskStatusOk() throws Exception {
		mockMvc.perform(get("/alltask"))
		// .andDo(print())
		.andExpect(status().isOk());
	}
	
	/**
	 * Creation of a task to do, with a title, an empty description. 
	 * I must have a title, a empty description and a date.
	 * @throws Exception
	 */
	@Test
	public void TestGetAllTask_InsertTaskInDb() throws Exception {
		Task task = new Task();
		task.setTitle("My new title");
		Task taskSave = taskRepository.save(task);
		assertEquals(task.getTitle(), taskSave.getTitle());
		assertNull(taskSave.getDescription());
		OffsetDateTime dateCreate = taskSave.getCreatedAt();
		assertNotNull(dateCreate);
		final String format = dateCreate.format(isoDateTime);
		mockMvc.perform(get("/alltask"))
		// .andDo(print())
		.andExpect(status().isOk())
	    .andExpect(jsonPath("$[3].id", is(taskSave.getId().intValue())))
		.andExpect(jsonPath("$[3].title", is(taskSave.getTitle())))
		.andExpect(jsonPath("$[3].description", is(taskSave.getDescription())))
		.andExpect(jsonPath("$[3].createdAt", is(format)));
	}

}
