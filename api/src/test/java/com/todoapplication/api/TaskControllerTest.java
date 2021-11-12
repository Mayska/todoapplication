package com.todoapplication.api;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
		final String title = "InsertTaskInDb";
		final Task task = newTask(title,null);
		final Task taskSave = taskRepository.save(task);
		assertTrue(taskSave.isState());
		assertEquals(task.getTitle(), taskSave.getTitle());
		assertEquals("",taskSave.getDescription());
		mockMvc.perform(get("/alltask"))
		//.andDo(print())
		.andExpect(status().isOk())
	    .andExpect(jsonPath("$[0].id", is(3)))
	    .andExpect(jsonPath("$[0].state", is(Boolean.TRUE)))
	    .andExpect(jsonPath("$[1].id", is(taskSave.getId().intValue())))
		.andExpect(jsonPath("$[1].title", is(taskSave.getTitle())))
		.andExpect(jsonPath("$[1].description", is(taskSave.getDescription())))
		.andExpect(jsonPath("$[1].createdAt", is(taskSave.getCreatedAt())))
		.andExpect(jsonPath("$[1].state", is(Boolean.TRUE)))
	    .andExpect(jsonPath("$[2].id", is(1)))
	    .andExpect(jsonPath("$[2].state", is(Boolean.FALSE)))
	    .andExpect(jsonPath("$[3].id", is(2)))
	    .andExpect(jsonPath("$[3].state", is(Boolean.FALSE)));
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
		// .andDo(print())
        .andExpect(status().isOk());
		final Task modifyTask = taskRepository.findById(1L).get();
		assertEquals(task.getTitle(), modifyTask.getTitle());
		assertEquals(task.getDescription(), modifyTask.getDescription());
		assertEquals(task.getCreatedAt(), modifyTask.getCreatedAt());
		assertFalse(modifyTask.isState());
		assertNotEquals(task.isState(), modifyTask.isState());
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
        .andExpect(status().is(400))
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
		final Task taskSave = taskRepository.save(task);
		assertTrue(taskSave.isState());
		assertEquals(task.getTitle(), taskSave.getTitle());
		assertEquals(task.getDescription(), taskSave.getDescription());
		mockMvc.perform(get("/findtask/{id}", taskSave.getId()))
		.andDo(print())
	    .andExpect(jsonPath("$.id", is(taskSave.getId().intValue())))
		.andExpect(jsonPath("$.title", is(taskSave.getTitle())))
		.andExpect(jsonPath("$.description", is(taskSave.getDescription())))
		.andExpect(jsonPath("$.createdAt", is(taskSave.getCreatedAt())))
		.andExpect(jsonPath("$.state", is(Boolean.TRUE)));
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
        .andExpect(status().is(400))
        .andExpect(status().reason(msgError))
        .andDo(print());
	}
	
	
	/**
	 * Create Task
	 * @param title
	 * @param description
	 * @return
	 */
	private Task newTask(String title, String description) {
		description = description != null ? description : "";		
		final Task task = new Task();
		task.setTitle(title);
		task.setDescription(description);
		task.setState(true);
		return task;
	}
}
