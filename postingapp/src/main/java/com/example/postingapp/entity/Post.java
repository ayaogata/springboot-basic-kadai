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
 * 投稿内容を保存するためのテーブルEntity
 * */

@Entity
@Table(name = "posts")
@Data
public class Post {
	
	//ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	//ユーザーID
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	//タイトル
	@Column(name = "title")
	private String title;
	
	//本文
	@Column(name = "content")
	private String content;
	
	//作成日時
	@Column(name="created_at", insertable=false, updatable=false)
	private Timestamp createdAt;
	
	//更新日時
	@Column(name="updated_at", insertable=false, updatable=false)
	private Timestamp updatedAt;

}
