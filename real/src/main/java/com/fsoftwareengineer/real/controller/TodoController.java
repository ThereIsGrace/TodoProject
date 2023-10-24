package com.fsoftwareengineer.real.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsoftwareengineer.real.dto.ResponseDto;
import com.fsoftwareengineer.real.dto.TodoDto;
import com.fsoftwareengineer.real.model.TodoEntity;
import com.fsoftwareengineer.real.service.TodoService;

@RestController
@RequestMapping("todo")
public class TodoController {
	// testTodo 메서드 작성하기 
	
	@Autowired
	private TodoService service;
	
	@GetMapping("/test")
	public ResponseEntity<?> testTodo(){
		String str = service.testService();
		List<String> list = new ArrayList<>();
		list.add(str);
		ResponseDto<String> response = ResponseDto.<String>builder().data(list).build();
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping
	public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDto dto){
		try {
			
			// (1) TodoEntity로 변환한다. 
			TodoEntity entity = TodoDto.toEntity(dto);
			
			// (2) id를 null로 초기화한다. 생성 당시에는 id가 없어야 하기 때문이다.
			entity.setId(null);
			
			// (3) Authentication Bearer Token을 통해 받은 userId를 넘긴다. 
			entity.setUserId(userId);
			
			// (4) 서비스를 이용해 Todo 엔티티를 생성한다. 
			List<TodoEntity> entities = service.create(entity);
			
			// (5) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDto 리스트로 변환한다. 
			List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());
			
			// (6) 변환된 TodoDto 리스트를 이용해 ResponseDto를 초기화한다. 
			ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(dtos).build();
			
			// (7) ResponseDto를 리턴한다. 
			return ResponseEntity.ok().body(response);
		}catch(Exception e) {
			// (8) 혹시 예외가 나는 경우 dto 대신 error에 메시지를 넣어 리턴한다. 
			String error = e.getMessage();
			ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId){
		
		// (1) 서비스 메서드의 retrieve 메서드를 사용해 Todo 리스트를 가져온다. 
		List<TodoEntity> entities = service.retrieve(userId);
		
		// (2) 자바스트림을 이용해 리턴된 엔티티 리스트를 TodoDto 리스트로 변환한다. 
		List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());

		// (3) 변환된 TodoDto 리스트를 이용해 ResponseDto를 초기화한다. 
		ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(dtos).build();
		
		// (4) ResponseDto를 리턴한다. 
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping
	public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDto dto){
		
		// (1) dto를 entity로 변환한다. 
		TodoEntity entity = TodoDto.toEntity(dto);
		
		// (2) Authentication Bearer Token을 통해 받은 userId를 넘긴다.
		entity.setUserId(userId);
		
		// (3) 서비스를 이용해 entity를 업데이트한다. 
		List<TodoEntity> entities = service.update(entity);
		
		// (4) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDto 리스트로 변환한다. 
		List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());
		
		// (5) 변환된 TodoDto 리스트를 이용해 ResponseDto를 초기화한다. 
		ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(dtos).build();
		
		// (6) ResponseDto를 리턴한다.
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDto dto){
		try {
			
			// (1) TodoEntity로 변환한다.
			TodoEntity entity = TodoDto.toEntity(dto);
			
			// (2) Authentication Bearer Token을 통해 받은 userId를 넘긴다.
			entity.setUserId(userId);
			
			// (3) 서비스를 이용해 entity를 삭제한다. 
			List<TodoEntity> entities = service.delete(entity);
			
			// (4) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDto 리스트로 변환한다. 
			List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());
			
			// (5) 변환된 TodoDto 리스트를 이용해 ResponseDto를 초기화한다. 
			ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(dtos).build();
			
			// (6) ResponseDto를 리턴한다. 
			return ResponseEntity.ok().body(response);
		}catch(Exception e) {
			
			// (7) 혹시 예외가 나는 경우 dto 대신 error에 메시지를 넣어 리턴한다. 
			String error = e.getMessage();
			ResponseDto<TodoDto> response  = ResponseDto.<TodoDto>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
}
