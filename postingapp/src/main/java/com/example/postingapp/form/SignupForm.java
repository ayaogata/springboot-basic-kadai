package com.example.postingapp.form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/*
 * 会員登録のフォームクラス
 * */

@Data
public class SignupForm {
	
	//ユーザー名
	@NotBlank(message = "ユーザー名を入力してください。")
	private String name;
	
	//メールアドレス
	@NotBlank(message = "メールアドレスを入力してください。")
	@Email(message = "メールアドレスは正しい形式で入力してください。")
	private String email;
	
	//パスワード
	@NotBlank(message = "パスワードを入力してください。")
	@Length(min=8, message="パスワードは８文字以上で入力してください。")
	private String password;
	
	//パスワード確認
	@NotBlank(message = "パスワード（確認用）を入力してください。")
	private String passwordConfirmation;
	
}
