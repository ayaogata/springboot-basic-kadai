package com.example.postingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.postingapp.entity.Post;
import com.example.postingapp.entity.User;

/*
 * postテーブルに対してCRUD処理を行うリポジトリ
 * */

public interface PostRepository extends JpaRepository<Post, Integer>{
	
	//userが投稿した情報を降順で取得するメソッド
	public List<Post> findByUserOrderByCreatedAtDesc(User user);
	
	//update古い順
	public List<Post> findByUserOrderByUpdatedAtAsc(User user);
	
	//idが最も大きい投稿を取得するメソッド //テスト用
	public Post findFirstByOrderByIdDesc();
	
}
