package com.example.postingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.postingapp.entity.VerificationToken;

/*
 * verificationTokenテーブルに対してCRUD処理を行うリポジトリ
 * */

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer>{
	
	//トークンに一致するデータをテーブルから探すメソッド
	public VerificationToken findByToken(String token);
	
}
