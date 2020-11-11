package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.UserEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class UserInfoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserEntity.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		UserEntity fixedDepositDetails = (UserEntity) target;
		String email = fixedDepositDetails.getEmail();

		if (email == null || "".equalsIgnoreCase(email)) {
			ValidationUtils.rejectIfEmptyOrWhitespace
					(errors, "email", "error.email.blank", "must not be blank");
		} else if (!email.contains("@")) {
			errors.rejectValue("email", "error.email.invalid", "not a well-formed email address");
		}
	}

}
