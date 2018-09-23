package com.brouwershuis.controller;


import org.apache.log4j.Logger;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
	private static final Logger LOGGER = Logger.getLogger(MainController.class);

	//for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();
		

		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
			model.addObject("username", userName);
		} else {
			model.setViewName("403");
		}
		return model;

	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String welcome(Model model, String error, String logout) {

		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "login";
	}

//	private void getPrincipal() {
//		String userName = null;
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//		Class<? extends Object> s = principal.getClass();
//		if (principal instanceof org.springframework.security.core.userdetails.User) {
//			CustomUserDetails u = (CustomUserDetails) principal;
//			
//			
//			User user = userService.findByUsername(u.getUsername());
//			String s1 = "";
//
//		}
//	}
	
}
