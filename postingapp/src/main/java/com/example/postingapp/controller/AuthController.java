package com.example.postingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.postingapp.entity.User;
import com.example.postingapp.entity.VerificationToken;
import com.example.postingapp.event.SignupEventPublisher;
import com.example.postingapp.form.SignupForm;
import com.example.postingapp.service.UserService;
import com.example.postingapp.service.VerificationTokenService;

import jakarta.servlet.http.HttpServletRequest;

/*
 *ログイン関係のコントローラー 
 * */

@Controller
public class AuthController {
	
	private final UserService userService;
	private final SignupEventPublisher signupEventPublisher;
	private final VerificationTokenService verificationTokenService;
	
	
	//コンストラクタ
	public AuthController(UserService userService, SignupEventPublisher signupEventPublisher, VerificationTokenService verificationTokenService) {
		
		this.userService = userService;
		this.signupEventPublisher = signupEventPublisher;
		this.verificationTokenService = verificationTokenService;
	}
	
	
	//ログインページ
	@GetMapping("/login")
	public String login() {
		
		return "auth/login";
	}
	
	
	//会員登録ページ
	@GetMapping("/signup")
	public String signup(Model model) {
		
		model.addAttribute("signupForm", new SignupForm());
		
		return "auth/signup";
	}
	
	
	//会員登録ページのフォーム内容を受け取り、バリデーション・認証メール送信を行うメソッド
	@PostMapping("/signup")
	public String signup(@ModelAttribute @Validated SignupForm signupForm,
							BindingResult bindingResult,
							RedirectAttributes redirectAttributes,
							HttpServletRequest httpServletRequest,
							Model model) {
		
		//メールが登録済みの場合エラー内容を追加
		if(userService.isEmailRegistered(signupForm.getEmail())) {
			
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。");
			bindingResult.addError(fieldError);
		}
		
		//パスワードとパスワード（確認用）の入力値が一致しない場合エラー内容を追加
		if(!userService.isSamePassword(signupForm.getPassword(), signupForm.getPasswordConfirmation())) {
			
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "password", "パスワードが一致しません。");
			bindingResult.addError(fieldError);
		}
		
		//エラーをモデルに格納
		if(bindingResult.hasErrors()) {
			
			model.addAttribute("signupForm", signupForm);
			
			return "auth/signup";
		}
		
		
		//認証メールを送る
		User createdUser = userService.createUser(signupForm);
		String requestUrl = new String(httpServletRequest.getRequestURL());
		signupEventPublisher.publishSignupEvent(createdUser,  requestUrl);
		redirectAttributes.addFlashAttribute("successMessage", "ご入力いただいたメールアドレスに認証メールを送信しました。");
		
		return "redirect:/login";
	}
	
	
	//メール添付URLが叩かれた後にトークンを調べてユーザーを有効にするメソッド
	@GetMapping("/signup/verify")
	public String verify(@RequestParam(name = "token") String token, Model model) {
		
		VerificationToken verificationToken= verificationTokenService.getVerificationToken(token);
		
		if(verificationToken != null) {
			
			User user = verificationToken.getUser();
			userService.enableUser(user);
			String successMessage = "会員登録が完了しました。";
			model.addAttribute("successMessage", successMessage);
			
		} else {
			
			String errorMessage = "トークンが無効です。";
			model.addAttribute("errorMessage", errorMessage);
			
		}
		
		return "auth/verify";
	}

}
