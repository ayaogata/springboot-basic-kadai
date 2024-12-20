package com.example.postingapp.form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/*
 * 投稿作成のフォームクラス
 * */

@Data
public class PostRegisterForm {
	
	//投稿タイトル
	@NotBlank(message = "タイトルを入力してください。")
	@Length(max = 40, message = "タイトルは40文字以内で入力してください。")
	private String title;
	
	//投稿内容
	@NotBlank(message = "本文を入力してください。")
	@Length(max = 200, message = "本文は200文字以内で入力してください。")
	private String content;
	
}
