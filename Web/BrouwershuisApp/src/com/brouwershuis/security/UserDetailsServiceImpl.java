package com.brouwershuis.security;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.brouwershuis.db.dao.UserDAO;
import com.brouwershuis.db.model.EnumRoles;
import com.brouwershuis.db.model.Role;
import com.brouwershuis.db.model.User;

public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger LOGGER = Logger.getLogger(UserDetailsServiceImpl.class);

	@EJB
	UserDAO userDao;

	@Override
	// @Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);

		if (user != null) {

			boolean isAdmin = false;

			Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
			for (Role role : user.getRoles()) {
				grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));

				if (EnumRoles.ROLE_ADMIN.toString().equals(role.getName()))
					isAdmin = true;
			}

			CustomUserDetails customUser = new CustomUserDetails(user.getUsername(), user.getPassword(), user.getId(),
					isAdmin, grantedAuthorities);

			return customUser;
		} else {
			LOGGER.error("Username is not found with for name: " + username);
			throw new UsernameNotFoundException("No user with username '" + username + "' foun!");
		}
	}
}
