package com.example.revpay_p2.service;

import com.example.revpay_p2.exception.AuthenticationException;
import com.example.revpay_p2.model.User;

public interface UserService {
	boolean registerUser(User user);

	User login(String emailOrPhone, String password) throws AuthenticationException;

	boolean changePassword(Long userId, String currentPassword, String newPassword);

	boolean resetPassword(String emailOrPhone, String securityAnswer, String newPassword);

}
