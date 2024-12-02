package com.example.springpractice.controller;

import org.springframework.core.Conventions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springpractice.form.ContactForm;

/*
 * バリデーションの結果に応じて、表示内容を切り替える
 * */

@Controller
public class ContractFormController {

	//お問い合わせフォーム
	@GetMapping("/form")
	public String form(Model model) {
		
		if(!model.containsAttribute("contactForm")) {
			model.addAttribute("contactForm", new ContactForm());
		}
		
		return "contractFormView";
	}
	
	
	//
	@PostMapping("/confirm")
	public String confirmForm(RedirectAttributes redirectAttributes,
			@Validated ContactForm form, BindingResult result) {
		
		redirectAttributes.addFlashAttribute("contactForm", form);
		
		if(result.hasErrors()) {
			redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + Conventions.getVariableName(form), result);
			return "redirect:/form";
		}
		
		return "confirmView";
	}
	
	
}
