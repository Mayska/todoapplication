package com.todoapplication.webapp;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.todoapplication.webapp.apiservice.TaskApiService;
import com.todoapplication.webapp.model.Task;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IndexControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TaskApiService taskApiService;
	
	
	/**
	 * Create one task
	 * @throws Exception
	 */
	@BeforeAll public void initialize() throws Exception {
			Task task = new Task();
			task.setTitle("My title");
			task.setDescription("My description");
			task.setState(true);
			taskApiService.saveNewTask(task);
	  }
	
	// Test all url
	
	/**
	 * Verify status 200 url "/" 
	 * @throws Exception
	 */
	@Test
	public void TestIndex_GetIndexUrl() throws Exception {
		mockMvc.perform(get("/"))
		//.andDo(print())
		.andExpect(status().isOk())
		.andReturn();
	}
	
	/**
	 * Verify status 200 url "/formtask"
	 * @throws Exception
	 */
	@Test
	public void TestIndex_GetFormUrl() throws Exception {
		mockMvc.perform(get("/formtask"))
		// .andDo(print())
		.andExpect(status().isOk());
	}
	
	/**
	 * Verify status 200 url "/finishtask/{id}" and test change todo to finsh
	 * @throws Exception
	 */
	@Test
	public void TestNewTask_GetFinishTask() throws Exception {
		MvcResult andReturn = mockMvc.perform(get("/"))
		//.andDo(print())
		.andExpect(status().isOk())
		.andReturn();
		String body = andReturn.getResponse().getContentAsString();
		assertTrue(body.contains("<label for=\"todo\">To do</label>"));
		mockMvc.perform(get("/finishtask/"+ 1 ))
		// .andDo(print())
		.andExpect(status().is(302));
		MvcResult andReturn1 = mockMvc.perform(get("/"))
		//.andDo(print())
		.andExpect(status().isOk())
		.andReturn();
		String body1 = andReturn1.getResponse().getContentAsString();
		assertTrue(body1.contains("<label for=\"todo\">Finish</label>"));
	}
	

}
