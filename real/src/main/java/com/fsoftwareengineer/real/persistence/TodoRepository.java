package com.fsoftwareengineer.real.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fsoftwareengineer.real.model.TodoEntity;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String>{
	@Query(value = "select * from Todo t where t.user_Id = ?", nativeQuery = true)
	List<TodoEntity> findByUserIdQuery(String userId);
	
	List<TodoEntity> findByUserId(String userId);
}
