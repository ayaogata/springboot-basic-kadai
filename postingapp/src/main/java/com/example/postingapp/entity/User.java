package com.example.postingapp.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/*
 * ユーザー情報を保存するためのテーブルEntity
 * */

@Entity
@Table(name = "users")
@Data
public class User {
	
	//ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	//ユーザー名
	@Column(name = "name")
	private String name;
	
	//メールアドレス
	@Column(name = "email")
	private String email;
	
	//パスワード
	@Column(name = "password")
	private String password;
	
	//役割ID
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	
	//ユーザーの有効フラグ
	@Column(name = "enabled")
	private Boolean enabled;
	
	//作成日時
	@Column(name="created_at", insertable=false, updatable=false)
	private Timestamp createdAt;
	
	//更新日時
	@Column(name = "updated_at", insertable=false, updatable=false)
	private Timestamp updatedAt;

}
