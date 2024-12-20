package com.example.postingapp.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.example.postingapp.entity.User;

/*
 * イベントトリガーを受取イベントを発行するクラス
 * */

@Component
public class SignupEventPublisher {
	
	private final ApplicationEventPublisher applicationEventPublisher;
	
	//コンストラクタ
	public SignupEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	//イベントを発行するメソッド
	public void publishSignupEvent(User user, String requestUrl) {
		
		//SignupEventクラスのインスタンスを渡してイベント発行
		applicationEventPublisher.publishEvent(new SignupEvent(this, user, requestUrl));
	}
}
