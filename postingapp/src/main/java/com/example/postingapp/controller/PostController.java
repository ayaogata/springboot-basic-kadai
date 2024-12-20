package com.example.postingapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.postingapp.entity.Post;
import com.example.postingapp.entity.User;
import com.example.postingapp.form.PostEditForm;
import com.example.postingapp.form.PostRegisterForm;
import com.example.postingapp.security.UserDetailsImpl;
import com.example.postingapp.service.PostService;

/*
 * 投稿関係のコントローラー
 * */

@Controller
//各メソッドに共通のパスを繰り返し記述する必要がない
@RequestMapping("/posts")
public class PostController {
	
	private final PostService postService;
	
	//コンストラクタ
	public PostController(PostService postService) {
		
		this.postService = postService;
	}
	
	//投稿一覧
	@GetMapping
	public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		
		User user = userDetailsImpl.getUser();
		//更新日時が古い順
		List<Post> posts = postService.findByUserOrderByUpdatedAtAsc(user);
		model.addAttribute("posts", posts);
		
		return "posts/index";
	}
	
	//投稿idを含めたURLで投稿内容を表示する
	@GetMapping("/{id}")
	public String show(@PathVariable(name="id") Integer id, RedirectAttributes redirectAttributes, Model model) {
		
		Optional<Post> optionalPost = postService.findPostById(id);
		
		//投稿が存在しない場合の処理
		if(optionalPost.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "投稿が存在しません。");
			
			return "redirect:/posts";
		}
		
		//エンティティの各フィールドにアクセスするため
		Post post = optionalPost.get();
		model.addAttribute("post", post);
		
		return "posts/show";
	}
	
	
	//投稿ページ
	@GetMapping("/register")
	public String register(Model model) {
		
		model.addAttribute("postRegisterForm", new PostRegisterForm());
		
		return "posts/register";
	}
	
	//投稿機能
	@PostMapping("/create")
	public String create(@ModelAttribute @Validated PostRegisterForm postRegisterForm,
							BindingResult bindingResult,
							@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
							RedirectAttributes redirectAttributes,
							Model model) {
		
		//入力などにエラーがあれば投稿作成ページを再表示
		if(bindingResult.hasErrors()) {
			
			model.addAttribute("postRegisterForm", postRegisterForm);
			
			return "posts/register";
		}
		
		User user = userDetailsImpl.getUser();
		
		//投稿をDBに追加
		postService.createPost(postRegisterForm, user);
		redirectAttributes.addFlashAttribute("successMessage", "投稿が完了しました。");
		
		return "redirect:/posts";
	}
	
	//編集ページ
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable(name = "id") Integer id,
						@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
						RedirectAttributes redirectAttributes,
						Model model) {
		
		Optional<Post> optionalPost = postService.findPostById(id);
		User user = userDetailsImpl.getUser();
		
		if(optionalPost.isEmpty() || !optionalPost.get().getUser().equals(user)) {
			
			redirectAttributes.addFlashAttribute("errorMessage", "不正なアクセスです。");
			
			return "redirect:/posts";
			
		}
		
		Post post = optionalPost.get();
		model.addAttribute("post", post);
		model.addAttribute("postEditForm", new PostEditForm(post.getTitle(), post.getContent()));
		
		return "posts/edit";
		
	}
	
	
	//投稿更新
	@PostMapping("/{id}/update")
	public String update(@ModelAttribute @Validated PostEditForm postEditForm,
						BindingResult bindingResult,
						@PathVariable(name = "id") Integer id,
						@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
						RedirectAttributes redirectAttributes,
						Model model) {
		
		Optional<Post> optionalPost = postService.findPostById(id);
		User user = userDetailsImpl.getUser();
		
		if(optionalPost.isEmpty() || !optionalPost.get().getUser().equals(user)) {
			
			redirectAttributes.addFlashAttribute("errorMessage", "不正なアクセスです。");
			
			return "redirect:/posts";
		}
		
		Post post = optionalPost.get();
		
		if(bindingResult.hasErrors()) {
			
			model.addAttribute("post", post);
			model.addAttribute("postEditForm", postEditForm);
			bindingResult.getAllErrors().forEach(error -> System.err.println("★★★" + error.getDefaultMessage())); 
			
			return "posts/edit";
		}
		
		postService.updatePost(postEditForm, post);
		redirectAttributes.addFlashAttribute("successMessage", "投稿を編集しました。");
		
		return "redirect:/posts/" + id;
	}
	
	
	//投稿削除
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable(name = "id") Integer id,
						@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
						RedirectAttributes redirectAttributes,
						Model model) {
		
		Optional<Post> optionalPost = postService.findPostById(id);
		User user = userDetailsImpl.getUser();
		
		if(optionalPost.isEmpty() || !optionalPost.get().getUser().equals(user)) {
			
			redirectAttributes.addFlashAttribute("errorMessage", "不正なアクセスです。");
			
			return "redirect:/posts";
		}
		
		Post post = optionalPost.get();
		postService.deletePost(post);
		redirectAttributes.addFlashAttribute("successMessage", "投稿を削除しました。");
		
		return "redirect:/posts";
		
	}
	
}
