package com.example.revpay_p2.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.revpay_p2.model.Notification;
import com.example.revpay_p2.model.User;
import com.example.revpay_p2.repository.NotificationRepository;
import com.example.revpay_p2.repository.UserRepository;
import com.example.revpay_p2.service.NotificationService;

@Service
@Transactional
public class NotificationServiceImplementation implements NotificationService {

	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImplementation.class);

	private final NotificationRepository notificationRepository;
	private final UserRepository userRepository;

	public NotificationServiceImplementation(NotificationRepository notificationRepository,
			UserRepository userRepository) {

		this.notificationRepository = notificationRepository;
		this.userRepository = userRepository;
	}

	// ================= SHOW UNREAD =================

	@Override
	public void showUnreadNotifications(Long userId) {

		try {
			User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

			List<Notification> notifications = notificationRepository.findByUserAndReadFalse(user);

			if (notifications.isEmpty()) {
				logger.info("No new notifications for user {}", userId);
				return;
			}

			logger.info("Unread notifications for user {}:", userId);

			for (Notification n : notifications) {

				logger.info(" - {} [{}]", n.getMessage(), n.getType());

				// mark as read
				n.setRead(true);
			}

			notificationRepository.saveAll(notifications);

		} catch (Exception e) {
			logger.error("Failed to fetch notifications for user {}", userId, e);
		}
	}
}
