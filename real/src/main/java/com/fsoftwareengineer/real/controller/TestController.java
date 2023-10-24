package com.fsoftwareengineer.real.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsoftwareengineer.real.dto.ResponseDto;
import com.fsoftwareengineer.real.dto.TestRequestBodyDTO;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@GetMapping
	public String testController() {
		return "Hello World!";
	}
	
	@GetMapping("/test/testGetMapping")
	public String testControllerWithPath() {
		return "Hello World! testGetMapping ";
	}
	
	@GetMapping("/{id}")
	public String testControllerWithPathVariables(@PathVariable(required = false) int id) {
		return "Hello World! ID " + id;
	}
	
	@GetMapping("/testRequestParam")
	public String testControllerRequestParam(@RequestParam(required=false) int id) {
		return "Hello World! ID " + id;
	}
	
	// /test 경로는 이미 존재하므로 /test/testRequestBody
	@GetMapping("/testRequestBody")
	public String testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
		return "Hello World! ID " + testRequestBodyDTO.getId() + " Message : " + testRequestBodyDTO.getMessage();
	}
	
	@GetMapping("/testResponseBody")
	public ResponseDto<String> testControllerResponseBody(){
		List<String> list = new ArrayList<>();
		list.add("Hello World! I'm a ResponseDTO");
		ResponseDto<String> response = ResponseDto.<String>builder().data(list).build();
		return response;
	}
	
	@GetMapping("/testResponseEntity")
	public ResponseEntity<?> testControllerResponseEntity(){
		List<String> list = new ArrayList<>();
		list.add("Hello World! I'm ResponseEntity. And you get 400!");
		ResponseDto<String> response = ResponseDto.<String>builder().data(list).build();
		// http status를 400로 설정.
		return ResponseEntity.ok().body(response);
	}
}
