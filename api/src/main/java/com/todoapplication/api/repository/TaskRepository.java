package com.todoapplication.api.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.todoapplication.api.model.Task;

/**
 * @author pc
 *
 */
public interface TaskRepository extends CrudRepository<Task, Long>{
	
	 Iterable<Task> findAll(Sort sort);

}
