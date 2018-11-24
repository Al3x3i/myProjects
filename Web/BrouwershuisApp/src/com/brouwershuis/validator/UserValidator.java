package com.brouwershuis.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.brouwershuis.db.model.User;
import com.brouwershuis.pojo.EmloyeePojo;
//import com.brouwershuis.security.UserService;
import com.brouwershuis.service.UserService;

@Component
public class UserValidator implements Validator {
	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}
	
	public enum ValidationType{
		ADDUSER,
		EDITUSER
	}
	
	private ValidationType validationType = ValidationType.ADDUSER;
	
	public void setValidationType(ValidationType validationType){
		this.validationType = validationType;
	}

	@Override
	public void validate(Object o, Errors errors) {

		EmloyeePojo user = (EmloyeePojo) o;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
		if (user.getFirstName().length() < 1 || user.getFirstName().length() > 32) {
			errors.reject("firstName", "Size.userForm.username");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");
		if (user.getLastName().length() < 1 || user.getLastName().length() > 32) {
			errors.reject("lastName", "Size.userForm.username");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "NotEmpty");
		if (user.getGender().equals("")) {
			errors.reject("gender", "Size.userForm.username");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfBirth", "NotEmpty");
		if (user.getDateOfBirth() == null) {
			errors.reject("dateOfBirth", "Size.userForm.username");
		}

		if (user.getUsername().length() != 0 && user.getPassword().length() != 0) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
			if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
				errors.reject("username", "Size.userForm.username");
			}
			
			
			if (ValidationType.EDITUSER == validationType){
				if (userService.findByUsername(user.getUsername()) != null) {
					errors.reject("username", "Duplicate.userForm.username");
				}
			}
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
			if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
				errors.reject("password", "Size.userForm.password");
			}

			if (!user.getConfirmPassword().equals(user.getPassword())) {
				errors.reject("confirmPassword", "Diff.userForm.passwordConfirm");
			}
		}
	}
}
