package com.example.postingapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.postingapp.entity.User;
import com.example.postingapp.entity.VerificationToken;
import com.example.postingapp.repository.VerificationTokenRepository;

/*
 * verification_tokensテーブルに関するサービス
 * */

@Service
public class VerificationTokenService {

	private final VerificationTokenRepository verificationTokenRepository;
	
	
	//コンストラクタ
	public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
		
		this.verificationTokenRepository = verificationTokenRepository;
	}
	
	
	//UUIDでランダム生成したトークンをテーブルに保存するメソッド
	@Transactional
	public void create(User user, String token) {
		
		VerificationToken verificationToken = new VerificationToken();
		
		verificationToken.setUser(user);
		verificationToken.setToken(token);
		
		verificationTokenRepository.save(verificationToken);
	}
	
	
	//トークンが一致するデータをテーブルから探すサービスクラス
	public VerificationToken getVerificationToken(String token) {
		
		return verificationTokenRepository.findByToken(token);
	}
	
}
