package com.example.springcore.ui;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.springcore.entity.User;
import com.example.springcore.service.UserService;

@Component
public class ConsoleUI {

    private final UserService userService;

    @Autowired
    public ConsoleUI(UserService userService) {
        this.userService = userService;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nUser Management System");
            System.out.println("1. Register User");
            System.out.println("2. Get User By ID");
            System.out.println("3. List All Users");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter User ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    userService.registerUser(id, name, email);
                }
                case 2 -> {
                    System.out.print("Enter User ID to search: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Optional<User> user = userService.getUserById(id);
                    if (user.isPresent()) {
                        System.out.println("Found: " + user.get());
                    } else {
                        System.out.println("User not found.");
                    }
                }
                case 3 -> {
                    Map<Integer, User> users = userService.getAllUsers();
                    if (users.isEmpty()) {
                        System.out.println("No users found.");
                    } else {
                        users.values().forEach(System.out::println);
                    }
                }
                case 4 -> {
                    running = false;
                    System.out.println("Exiting application.");
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }

        scanner.close();
    }
}

