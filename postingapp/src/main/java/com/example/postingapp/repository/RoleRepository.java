package com.example.postingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.postingapp.entity.Role;

/*
 * Rolesテーブルに対してCRUD処理を行うリポジトリ
 * */

public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	//役割名一致を検索するメソッド
	public Role findByName(String name);

}
