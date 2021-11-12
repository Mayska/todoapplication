package com.todoapplication.webapp.model;

import java.util.Date;

/**
 * Model Task
 * @author pc
 *
 */
public class Task {

	/**
	 * task id
	 */
	private Long id;
	
	/**
	 * task title.
	 */
	private String title;
	
	/**
	 * task description.
	 */
	private String description;

	/**
	 * task creation date.
	 */
	private String createdAt;
	
	/**
	 * task state : true must to do / false is finish
	 */
	private boolean state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

//	public Date getCreatedAt() {
//		return createdAt;
//	}
//
//	public void setCreatedAt(Date createdAt) {
//		this.createdAt = createdAt;
//	}
}
