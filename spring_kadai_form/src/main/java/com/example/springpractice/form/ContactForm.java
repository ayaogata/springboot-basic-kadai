package com.example.springpractice.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/*
 * フォームクラスの定義
 * */

@Data
public class ContactForm {
	
	@NotBlank(message = "お名前を入力してください。")
	private String name;
	
	@NotBlank(message = "メールアドレスを入力してください。")
	@Email(message = "メールアドレスの入力形式が正しくありません。")
	private String email;
	
	@NotBlank(message = "お問い合わせ内容を入力してください。")
	@Size(max=300, message="300文字までで記入してください。" )
	private String message;
}
