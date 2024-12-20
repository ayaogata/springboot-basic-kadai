package com.example.postingapp.form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * 編集ページのフォームクラス
 * */

@Data
@AllArgsConstructor //全フィールドに値をセットするための引数付きコンストラクタを自動生成
public class PostEditForm {
	
	//タイトル
	@NotBlank(message = "タイトルを入力してください。")
	@Length(max = 40, message = "タイトルは40文字以内で入力してください。")
	private String title;
	
	//内容
	@NotBlank(message = "本文を入力してください。")
	@Length(max = 200, message = "本文は200文字以内で入力してください。")
	private String content;
}
