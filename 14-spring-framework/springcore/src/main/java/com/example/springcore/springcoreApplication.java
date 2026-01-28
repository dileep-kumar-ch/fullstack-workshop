package com.example.springcore;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.springcore.config.AppConfig;
import com.example.springcore.ui.ConsoleUI;

public class springcoreApplication {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		ConsoleUI ui = context.getBean(ConsoleUI.class);
		ui.run();
	}
}
