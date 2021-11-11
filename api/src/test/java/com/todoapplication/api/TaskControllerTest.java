package com.todoapplication.api;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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
		final Task task = newTask("InsertTaskInDb");
		final Task taskSave = taskRepository.save(task);
		assertTrue(taskSave.isState());
		assertEquals(task.getTitle(), taskSave.getTitle());
		assertNull(taskSave.getDescription());
		mockMvc.perform(get("/alltask"))
		// .andDo(print())
		.andExpect(status().isOk())
	    .andExpect(jsonPath("$[3].id", is(taskSave.getId().intValue())))
		.andExpect(jsonPath("$[3].title", is(taskSave.getTitle())))
		.andExpect(jsonPath("$[3].description", is(taskSave.getDescription())))
		//.andExpect(jsonPath("$[3].createdAt", is(taskSave.getCreatedAt())))
		.andExpect(jsonPath("$[3].state", is(Boolean.TRUE)));
	}

	
	
	/**
	 * task state is true after update it is false
	 * @throws Exception
	 */
	@Test
	public void TestUpdateStateTask_findTaskAndChangeState() throws Exception {
		final Task task = taskRepository.findById(1L).get();
		assertTrue(task.isState());
		mockMvc.perform(get("/finishtask/" +  task.getId()))
        .andExpect(status().isOk())
        .andDo(print());
		final Task modifyTask = taskRepository.findById(1L).get();
		assertFalse(modifyTask.isState());
	}
	
	/**
	 * Test update with a non-existent id.
	 * @throws Exception
	 */
	@Test
	public void TestUpdateStateTask_findTaskWithBadId() throws Exception {
		final Long id = 999L;
		final String msgError = TaskConstant.ERROR_TASK_ID + id;
		mockMvc.perform(get("/finishtask/" +  id))
        .andExpect(status().is(400))
        .andExpect(status().reason(msgError))
        .andDo(print());
	}
	
	private Task newTask(String title) {
		final Task task = new Task();
		task.setTitle(title);
		task.setState(true);
		return task;
	}
}
