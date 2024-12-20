package com.example.postingapp.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.postingapp.entity.Post;
import com.example.postingapp.service.PostService;

/*
 * 投稿テストに関するコントローラー
 * */

//テスト時にアプリのコンテキストを起動する
@SpringBootTest 
//MockMvcインスタンスを注入できるようにする
@AutoConfigureMockMvc 
//テスト時に異なる設定ファイルを適用するため
//「application-○○○.properties」の「○○○」の部分
@ActiveProfiles("test") 
public class PostControllerTest {
	
	//SpringBootの機能の１つ。MVCの動作を模倣することでリクエスト・レスポンスの結果を検証できる
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PostService postService;
	
	//投稿一覧ページ
	//テスト時に実行される
	@Test
	//ログイン済みユーザーとして振る舞う用
	@WithUserDetails("taro.samurai@example.com")
	public void ログイン済みの場合は投稿一覧ページが正しく表示される() throws Exception{
		
		//HTTPリクエストを送信する振る舞い
		mockMvc.perform(get("/posts"))
				//HTTPレスポンスが期待する内容かどうかの検証
				.andExpect(status().isOk())
				.andExpect(view().name("posts/index"));
		
	}
	
	//投稿一覧ページ
	@Test
	public void 未ログインの場合は投稿一覧ページからログインページにリダイレクトする() throws Exception{
		
		mockMvc.perform(get("/posts"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
		
	}
	
	//投稿詳細ページ
	@Test
	@WithUserDetails("taro.samurai@example.com")
	public void ログイン済みの場合は投稿詳細ページが正しく表示される() throws Exception{
		
		mockMvc.perform(get("/posts/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("posts/show"));
	
	}
	
	//投稿詳細ページ
	@Test
	public void 未ログインの場合は投稿詳細ページからログインページにリダイレクトする() throws Exception{
		
		mockMvc.perform(get("/posts/1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
	}
	
	//投稿ページ
	@Test
	@WithUserDetails("taro.samurai@example.com")
	public void ログイン済みの場合は投稿作成ページが正しく表示される() throws Exception{
		
		mockMvc.perform(get("/posts/register"))
				.andExpect(status().isOk())
				.andExpect(view().name("posts/register"));
		
	}
	
	//投稿ページ
	@Test
	public void 未ログインの場合は投稿作成ページからログインページにリダイレクトする() throws Exception{
		
		mockMvc.perform(get("/posts/register"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
		
	}
	
	//投稿作成機能
	@Test
	@WithUserDetails("taro.samurai@example.com")
	@Transactional
	public void ログイン済みの場合は投稿作成後に投稿一覧ページにリダイレクトする() throws Exception{
		
		mockMvc.perform(post("/posts/create").with(csrf()).param("title", "テストタイトル").param("content", "テスト内容"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/posts"));
		
		Post post = postService.findFirstPostByOrderByIdDesc();
		assertThat(post.getTitle()).isEqualTo("テストタイトル");
		assertThat(post.getContent()).isEqualTo("テスト内容");
		
	}
	
	@Test
	@Transactional
	public void 未ログインの場合は投稿を作成せずにログインページにリダイレクトする() throws Exception{
		
		mockMvc.perform(post("/posts/create").with(csrf()).param("title", "テストタイトル").param("content", "テスト内容"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
		
		Post post = postService.findFirstPostByOrderByIdDesc();
		assertThat(post.getTitle()).isNotEqualTo("テストタイトル");
		assertThat(post.getContent()).isNotEqualTo("テスト内容");
	}
	
	//投稿編集機能
	@Test
	@WithUserDetails("taro.samurai@example.com")
	public void ログイン済みの場合は自身の投稿編集ページが正しく表示される() throws Exception{
		
		mockMvc.perform(get("/posts/1/edit"))
				.andExpect(status().isOk())
				.andExpect(view().name("posts/edit"));
	}
	
	@Test
	@WithUserDetails("jiro.samurai@example.com")
	public void ログイン済みの場合は他人の投稿編集ページから投稿一覧ページにリダイレクトする() throws Exception{
		
		mockMvc.perform(get("/posts/1/edit"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/posts"));
			
	}
	
	@Test
	public void 未ログインの場合は投稿編集ページからログインページにリダイレクトする() throws Exception{
		
		mockMvc.perform(get("/posts/1/edit"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
	}
	
	
	//投稿更新機能
	@Test
	@WithUserDetails("taro.samurai@example.com")
	@Transactional
	public void ログイン済みの場合は自身の投稿更新後に投稿詳細ページにリダイレクトする() throws Exception{
		
		mockMvc.perform(post("/posts/1/update").with(csrf()).param("title", "テストタイトル").param("content", "テスト内容"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/posts/1"));
		
		Optional<Post> optionalPost = postService.findPostById(1);
		assertThat(optionalPost).isPresent();
		Post post = optionalPost.get();
		assertThat(post.getTitle()).isEqualTo("テストタイトル");
		assertThat(post.getContent()).isEqualTo("テスト内容");
	}
	
	@Test
	@WithUserDetails("jiro.samurai@example.com")
	@Transactional
	public void ログイン済みの場合は他人の投稿を更新せずに投稿一覧ページにリダイレクトする() throws Exception{
		
		mockMvc.perform(post("/posts/1/update").with(csrf()).param("title", "テストタイトル").param("content", "テスト内容"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/posts"));
		
		Optional<Post> optionalPost = postService.findPostById(1);
		assertThat(optionalPost).isPresent();
		Post post = optionalPost.get();
		assertThat(post.getTitle()).isNotEqualTo("テストタイトル");
		assertThat(post.getContent()).isNotEqualTo("テスト内容");
	}
	
	@Test
	@Transactional
	public void 未ログインの場合は投稿を更新せずにログインページにリダイレクトする() throws Exception{
		
		mockMvc.perform(post("/posts/1/update").with(csrf()).param("title", "テストタイトル").param("content", "テスト内容"))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("http://localhost/login"));

		Optional<Post> optionalPost = postService.findPostById(1);
		assertThat(optionalPost).isPresent();
		Post post = optionalPost.get();
		assertThat(post.getTitle()).isNotEqualTo("テストタイトル");
		assertThat(post.getContent()).isNotEqualTo("テスト内容");
	}
	
	
	//投稿削除機能
	@Test
	@WithUserDetails("taro.samurai@example.com")
	@Transactional
	public void ログイン済みの場合は自身の投稿削除後に投稿一覧ページにリダイレクトする() throws Exception{
		
		mockMvc.perform(post("/posts/1/delete").with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/posts"));
		
		Optional<Post> optionalPost = postService.findPostById(1);
		assertThat(optionalPost).isEmpty();
	}
	
	@Test
	@WithUserDetails("jiro.samurai@example.com")
	@Transactional
	public void ログイン済みの場合は他人の投稿を削除せずに投稿一覧ページにリダイレクトする() throws Exception{
		
		mockMvc.perform(post("/posts/1/delete").with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/posts"));
		
		Optional<Post> optionalPost = postService.findPostById(1);
		assertThat(optionalPost).isPresent();
	}
	
	@Test
	@Transactional
	public void 未ログインの場合は投稿を削除せずにログインページにリダイレクトする() throws Exception{
		
		mockMvc.perform(post("/posts/1/delete").with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
		
		Optional<Post> optionalPost = postService.findPostById(1);
		assertThat(optionalPost).isPresent();
	}
}
