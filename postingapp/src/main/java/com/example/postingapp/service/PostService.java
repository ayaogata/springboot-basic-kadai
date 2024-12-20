package com.example.postingapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.postingapp.entity.Post;
import com.example.postingapp.entity.User;
import com.example.postingapp.form.PostEditForm;
import com.example.postingapp.form.PostRegisterForm;
import com.example.postingapp.repository.PostRepository;

/*
 * postsテーブルに関するサービス
 * */

@Service
public class PostService {
	
	private final PostRepository postRepository;
	
	//コンストラクタ
	public PostService(PostRepository postRepository) {
		
		this.postRepository = postRepository;
	}
	
	
	//特定のユーザーに紐づく投稿の一覧を作成日時が新しい順で取得するメソッド
	public List<Post> findPostsByUserOrderedByCreatedAtDesc(User user){
		
		return postRepository.findByUserOrderByCreatedAtDesc(user);
	}
	
	
	//指定したidを持つ投稿を取得するメソッド
	public Optional<Post> findPostById(Integer id){
		
		return postRepository.findById(id);
	}
	
	
	//idが最も大きい投稿を取得するメソッド
	public Post findFirstPostByOrderByIdDesc() {
		
		return postRepository.findFirstByOrderByIdDesc();
	}
	
	//更新日順に並べるメソッド
	public List<Post> findByUserOrderByUpdatedAtAsc(User user) {
		
		return postRepository.findByUserOrderByUpdatedAtAsc(user);
	}
	
	
	//投稿作成の機能メソッド
	@Transactional
	public void createPost(PostRegisterForm postRegisterForm, User user) {
		
		Post post = new Post();
		post.setTitle(postRegisterForm.getTitle());
		post.setContent(postRegisterForm.getContent());
		post.setUser(user);
		
		postRepository.save(post);
	}
	
	
	//投稿更新の機能メソッド
	@Transactional
	public void updatePost(PostEditForm postEditForm, Post post) {
		
		post.setTitle(postEditForm.getTitle());
		post.setContent(postEditForm.getContent());
		
		postRepository.save(post);
	}
	
	
	//投稿削除の機能メソッド
	@Transactional
	public void deletePost(Post post) {
		
		postRepository.delete(post);
	}
}