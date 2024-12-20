package com.example.postingapp.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.postingapp.entity.User;

/*
 * DBにあるユーザーのログイン情報を管理するクラス
 * */

public class UserDetailsImpl implements UserDetails{
	
	private final User user;
	private final Collection<GrantedAuthority> authorities;
	
	//コンストラクタ
	public UserDetailsImpl(User user, Collection<GrantedAuthority> authorities) {
		this.user = user;
		this.authorities = authorities;
	}
	
	//
	public User getUser() {
		return user;
	}
	
	//ハッシュ化済みのパスワード
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	//ログイン時に利用するユーザー名
	@Override
	public String getUsername() {
		return user.getEmail();
	}
	
	//権限のコレクション
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return authorities;
	}
	
	/* 以下今回の仕様で全てtrueを返すようにした*/
	
	//アカウントが期限切れでなければtrue
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	//ユーザーがロックされていなければtrue
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	//ユーザーのパスワードが期限切れでなければtrue
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	//ユーザーが有効であればtrue
	@Override
	public boolean isEnabled() {
		return user.getEnabled();
	}
	
//	//認証状態を判断するメソッド
//	isAuthenticated()

}
