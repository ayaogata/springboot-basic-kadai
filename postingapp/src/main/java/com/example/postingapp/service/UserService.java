package com.example.postingapp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.postingapp.entity.Role;
import com.example.postingapp.entity.User;
import com.example.postingapp.form.SignupForm;
import com.example.postingapp.repository.RoleRepository;
import com.example.postingapp.repository.UserRepository;

/*
 * usersテーブルに関するサービス
 * */

@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	//コンストラクタ
	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	//DBにユーザーを追加するメソッド
	@Transactional
	public User createUser(SignupForm signupForm) {
		
		User user = new User();
		Role role = roleRepository.findByName("ROLE_GENERAL");
		
		user.setName(signupForm.getName());
		user.setEmail(signupForm.getEmail());
		user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
		user.setRole(role);
		user.setEnabled(false);
		
		return userRepository.save(user);
		
	}
	
	
	//メールアドレスが登録済みかどうかをチェックするメソッド
	public boolean isEmailRegistered(String email) {
		
		User user = userRepository.findByEmail(email);
		return user != null;
	}
	
	
	//パスワードとパスワード（確認用）の入力値が一致するかどうかをチェックするメソッド
	public boolean isSamePassword(String password, String passwordConfirmation) {
		
		return password.equals(passwordConfirmation);
	}
	
	
	//メール認証後にユーザーを有効にするメソッド
	@Transactional
	public void enableUser(User user) {
		
		user.setEnabled(true);
		userRepository.save(user);
	}
	
}
