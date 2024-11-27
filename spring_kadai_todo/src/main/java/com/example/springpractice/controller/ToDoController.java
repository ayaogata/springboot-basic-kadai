package com.example.springpractice.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.springpractice.entity.ToDo;
import com.example.springpractice.service.ToDoService;

@Controller
public class ToDoController {
	private final ToDoService todoService;
	
	//DI
	public ToDoController(ToDoService todoService) {
		this.todoService = todoService;
	}
	
	//
	@GetMapping("/todo")
	public String ToDoList(Model model) {
		List<ToDo> todoList = todoService.getAllToDo();
		model.addAttribute("todo", todoList);
		return "todoView";
	}

}
