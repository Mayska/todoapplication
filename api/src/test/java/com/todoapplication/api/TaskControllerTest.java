package com.todoapplication.api;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.OffsetDateTime;
import java.util.Date;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapplication.api.apiservice.TaskApiService;
import com.todoapplication.api.constant.TaskConstant;
import com.todoapplication.api.model.Task;
import com.todoapplication.api.repository.TaskRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private TaskApiService taskApiService;
	
	private final Long ID = 999L;
	
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
	 * Order by state
	 * @throws Exception
	 */
	@Test
	
	public void TestGetAllTask_InsertTaskInDb() throws Exception {
		final Task task0 = newTask("Title 0",null);
		final Task taskSave0 = taskApiService.createNewTask(task0);
		final Task task1 = newTask("Title 1","Description 1");
		final Task taskSave1 = taskApiService.createNewTask(task1);
		final Task task2 = newTask("Title 2",null);
		final Task taskSave2 = taskApiService.createNewTask(task2);
		final Task task3 = newTask("Title 3","Description 3");
		final Task taskSave3 = taskApiService.createNewTask(task3);
		final Task updateStateTask0 = taskApiService.updateStateTask(taskSave0.getId());
		final Task updateStateTask3 = taskApiService.updateStateTask(taskSave3.getId());
		mockMvc.perform(get("/alltask"))
		.andDo(print())
		.andExpect(status().isOk())
	    .andExpect(jsonPath("$[0].id", is(taskSave1.getId().intValue())))
	    .andExpect(jsonPath("$[0].state", is(true)))
	    .andExpect(jsonPath("$[1].id", is(taskSave2.getId().intValue())))
	    .andExpect(jsonPath("$[1].state", is(true)))
	    .andExpect(jsonPath("$[2].id", is(updateStateTask0.getId().intValue())))
	    .andExpect(jsonPath("$[2].state", is(false)))
	    .andExpect(jsonPath("$[3].id", is(updateStateTask3.getId().intValue())))
	    .andExpect(jsonPath("$[3].state", is(false)));
	}
	
	/**
	 * task state is true after update it is false
	 * @throws Exception
	 */
	@Test
	public void TestUpdateStateTask_findTaskAndChangeState() throws Exception {
		final Task task = newTask("newTask",null);
		final Task newTask = taskApiService.createNewTask(task);
		mockMvc.perform(get("/finishtask/" +  newTask.getId()))
		// .andDo(print())
        .andExpect(status().isOk());
		final Task modifyTask = taskRepository.findById(newTask.getId()).get();
		assertEquals(task.getTitle(), modifyTask.getTitle());
		assertEquals(task.getDescription(), modifyTask.getDescription());
		//assertEquals(task.getCreatedAt(), modifyTask.getCreatedAt());
		assertFalse(modifyTask.isState());
		assertNotEquals(task.isState(), modifyTask.isState());
		taskRepository.delete(modifyTask);
	}
	
	/**
	 * Test update with a non-existent id.
	 * @throws Exception
	 */
	@Test
	public void TestUpdateStateTask_findTaskWithBadId() throws Exception {		
		final String msgError = TaskConstant.ERROR_TASK_ID + ID;
		mockMvc.perform(get("/finishtask/" +  ID))
		// .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(status().reason(msgError));
	}
	
	/**
	 * Find my new task by id
	 * @throws Exception
	 */
	@Test
	public void TestGetTaskById_findTaskById() throws Exception {
		final String title = "My newTask";
		final String description = "My new description";
		final Task task = newTask(title,description);
		final Task taskSave = taskApiService.createNewTask(task);
		String createdAt = taskSave.getCreatedAt();
		mockMvc.perform(get("/findtask/{id}", taskSave.getId()))
		.andDo(print())
	    .andExpect(jsonPath("$.id", is(taskSave.getId().intValue())))
		.andExpect(jsonPath("$.title", is(taskSave.getTitle())))
		.andExpect(jsonPath("$.description", is(taskSave.getDescription())))
		.andExpect(jsonPath("$.createdAt", is(createdAt)))
		.andExpect(jsonPath("$.state", is(true)));
		taskRepository.delete(taskSave);
	}
	
	/**
	 * Find task by a non-existent id.
	 * @throws Exception
	 */
	@Test
	public void TestGetTaskById_findTaskWithBadId() throws Exception {
		final String msgError = TaskConstant.ERROR_TASK_ID + ID;
		mockMvc.perform(get("/findtask/{id}", ID))
        .andExpect(status().isBadRequest())
        .andExpect(status().reason(msgError))
        .andDo(print());
	}
	
	/**
	 * Create new task
	 * @throws Exception
	 */
	@Test
	public void TestSubmitTask_myNewTask() throws Exception {
		final Task newTask = newTask("Mon titre","Ma description");
        String writeValueAsString = new ObjectMapper().writeValueAsString(newTask);
        this.mockMvc.perform(post("/newtask")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsString))
                .andDo(print())
                .andExpect(status().isOk());
	}
	
	@Test
	public void TestSubmitTask_titleEmpty() throws Exception {
		final String msgError = TaskConstant.ERROR_TASK_TITLE_EMPTY;
		final Task newTask = newTask(null, null);
        String writeValueAsString = new ObjectMapper().writeValueAsString(newTask);
		this.mockMvc.perform(post("/newtask")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsString))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(msgError));
	}
	
	/**
	 * Create Task
	 * @param title
	 * @param description
	 * @return
	 */
	private Task newTask(String title, String description) {
		description = description != null ? description : "";
		title = title != null ? title : "";
		final Task task = new Task();
		task.setTitle(title);
		task.setDescription(description);
		return task;
	}
}
