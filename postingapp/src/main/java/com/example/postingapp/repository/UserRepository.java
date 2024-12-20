package com.example.postingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.postingapp.entity.User;

/*
 * usersテーブルに対してCRUD処理を行うリポジトリ
 * */

public interface UserRepository extends JpaRepository<User, Integer>{
	
	//メールでユーザーを検索するメソッド
	public User findByEmail(String email);
}
