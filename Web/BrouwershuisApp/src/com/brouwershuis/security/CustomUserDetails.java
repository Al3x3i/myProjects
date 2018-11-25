package com.brouwershuis.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 2092193994391577927L;

	private Long userId;
	private boolean isAdming;

	public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		// TODO Auto-generated constructor stub
	}

	public CustomUserDetails(String username, String password, Long id, boolean adminRole,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, true, true, true, true, authorities);
		this.userId = id;
		this.isAdming = adminRole;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isAdming() {
		return isAdming;
	}

	public void setAdming(boolean isAdming) {
		this.isAdming = isAdming;
	}
}
