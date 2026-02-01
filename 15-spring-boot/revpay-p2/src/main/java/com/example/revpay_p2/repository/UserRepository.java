package com.example.revpay_p2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.revpay_p2.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	 Optional<User> findByEmailOrPhone(String email, String phone);

	    // custom queries for failed attempts

//	    @Query("SELECT f.attempts FROM FailedLogin f WHERE f.user.id = :userId")
//	    Optional<Integer> findFailedAttemptsByUserId(@Param("userId") Long userId);

//	    @Modifying
//	    @Query("UPDATE FailedLogin f SET f.attempts = :attempts, f.lastAttempt = CURRENT_TIMESTAMP WHERE f.user.id = :userId")
//	    void saveFailedAttempts(@Param("userId") Long userId,
//	                            @Param("attempts") int attempts);
//
//	    @Modifying
//	    @Query("DELETE FROM FailedLogin f WHERE f.user.id = :userId")
//	    void resetFailedAttempts(@Param("userId") Long userId);
	    
	    Optional<User> findByEmail(String email);

	    Optional<User> findByPhone(String phone);


}
