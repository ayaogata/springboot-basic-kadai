package com.example.postingapp.event;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.postingapp.entity.User;

/*
 *イベントからの通知を受けメール認証用のメールを送信するListenerクラス 
 * */

//ListenerクラスのインスタンスをDIコンテナに登録
@Component
public class SignupEventListener {
	
//	private final VerificationTokenService verificationTokenService;
//	private final JavaMailSender javaMailSender;

//	//コンストラクタ
//	public SignupEventListener(VerificationTokenService verificationTokenService, JavaMailSender mailSender) {
//		
//		this.verificationTokenService = verificationTokenService;
//		this.javaMailSender = mailSender;
//	}
	
	//signupEventの発生時に実行
	@EventListener
	private void onSignupEvent(SignupEvent signupEvent) {
		
		//ログインしたいユーザー
		User user = signupEvent.getUser();
		//トークンUUIDの生成
		String token = UUID.randomUUID().toString();
		//メールを送るためのデータ保存
//				verificationTokenService.create(user,  token);
//				
//				String senderAddress = "springboot.postingapp@example,com";
//				String recipientAddress = user.getEmail();
//				String subject = "メール認証";
//				String confirmationUrl = signupEvent.getRequestUrl() + "/verify?token=" + token;
//				String message = "以下のリンクをクリックして会員登録を完了してください。";
//				
//				SimpleMailMessage mailMessage = new SimpleMailMessage();
//				//送信元のメールアドレス
//				mailMessage.setFrom(senderAddress);
//				//送信先のメールアドレス
//				mailMessage.setTo(recipientAddress);
//				//件名をセット
//				mailMessage.setSubject(subject);
//				//本文をセット
//				mailMessage.setText(message + "\n" + confirmationUrl);
				
	}
}
