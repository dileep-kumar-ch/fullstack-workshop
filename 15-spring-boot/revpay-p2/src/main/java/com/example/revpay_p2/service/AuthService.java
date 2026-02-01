package com.example.revpay_p2.service;

import com.example.revpay_p2.model.User;

public interface AuthService {

	void register(User user);

	User login(String emailOrPhone, String password);

	boolean changePassword(Long userId, String currentPassword, String newPassword);

}
