package com.example.postingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * ログイン後トップページのコントローラー
 * */

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String index() {
		//return "index";
		return "redirect:/posts";
	}
	
}
