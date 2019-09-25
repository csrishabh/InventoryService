package com.mongo.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mongo.demo.document.AppResponse;
import com.mongo.demo.document.User;
import com.mongo.demo.repo.UserRepository;
import com.mongo.utility.StringConstant;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private HashMap<String, User> cache  = new HashMap<>();
	
	public User findUserByEmail(String email) {
		if(cache.get(email)!=null) {
			return cache.get(email);
		}
		else {
			User user = userRepository.findByUsername(email);
			cache.put(email, user);
			return user;
		}
	}
	
	/*public void saveUser(User user) {
	    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	    user.setEnabled(true);
	    user.setRoles(user.getRoles());
	    userRepository.save(user);
	}*/
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(email);
	    if(user != null) {
	        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
	        //List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("admin"));
	        return buildUserForAuthentication(user, authorities);
	    } else {
	        throw new UsernameNotFoundException("username not found");
	    }
	}
	
	public AppResponse<String> resetPassword(HashMap<String, String> param) {

		AppResponse<String> response = new AppResponse<>();
		try {
			String currPass = param.get("currPass");
			String newPass = param.get("newPass");
			if (!StringUtils.isEmpty(currPass) && !StringUtils.isEmpty(newPass)) {
				String userId = SecurityContextHolder.getContext().getAuthentication().getName();
				User user = findUserByEmail(userId);
				if(bCryptPasswordEncoder.matches(currPass, user.getPassword())) {
					user.setPassword(bCryptPasswordEncoder.encode(newPass));
					userRepository.save(user);
					cache.put(userId, user);
					response.setSuccess(true);
					response.setMsg(Arrays.asList(StringConstant.PASS_CHANGE_SUCCESS));
				}
				else {
					response.setSuccess(false);
					response.setMsg(Arrays.asList(StringConstant.PASS_NOT_CORRECT));
				}
			} else {
				response.setSuccess(false);
				response.setMsg(Arrays.asList(StringConstant.TRY_AGAIN));
			}
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMsg(Arrays.asList(StringConstant.TRY_AGAIN));
		}
		return response;
	}
	
	private List<GrantedAuthority> getUserAuthority(Set<String> userRoles) {
	    Set<GrantedAuthority> roles = new HashSet<>();
	    userRoles.forEach((role) -> {
	        roles.add(new SimpleGrantedAuthority(role));
	    });

	    List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
	    return grantedAuthorities;
	}

	
	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
	    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}

}
