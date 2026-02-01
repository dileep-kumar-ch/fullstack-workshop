package com.example.revpay_p2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.revpay_p2.model.Notification;
import com.example.revpay_p2.model.User;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Modifying
    @Query(value = "INSERT INTO notifications (user_id, message, type) VALUES (?1, ?2, ?3)", nativeQuery = true)
    void saveLoginNotification(Long userId, String message, String type);
    
    List<Notification> findByUser(User user);

    List<Notification> findByUserAndReadFalse(User user);
}