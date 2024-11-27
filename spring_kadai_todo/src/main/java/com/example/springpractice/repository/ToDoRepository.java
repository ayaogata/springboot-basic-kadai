package com.example.springpractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springpractice.entity.ToDo;

public interface ToDoRepository extends JpaRepository<ToDo, Integer>{
	
}
