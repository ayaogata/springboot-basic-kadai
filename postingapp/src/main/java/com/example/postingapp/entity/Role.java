package com.example.postingapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/*
 * ユーザーの役割を保存するためのテーブルEntity
 * */

@Entity
@Table(name = "roles")
@Data
public class Role {
	
	//役割ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	//役割名
	@Column(name = "name")
	private String name;
	
}
