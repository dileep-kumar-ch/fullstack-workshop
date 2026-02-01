package com.example.revpay_p2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.revpay_p2.controller.AuthController;
import com.example.revpay_p2.service.AuthService;

@SpringBootTest
class RevpayP2ApplicationTests {

	@Test
	void applicationContextLoadsSuccessfully() {
		// If Spring Boot starts without exceptions → test PASSES
	}
	
	  @Autowired
	    private AuthController authController;

	    @Autowired
	    private AuthService authService;

	    @Test
	    void contextLoads() {
	        assertThat(authController).isNotNull();
	        assertThat(authService).isNotNull();
	    }

}
