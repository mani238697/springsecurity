package com.User.SpringSecurity.controllers;

import com.User.SpringSecurity.entities.User;
import com.User.SpringSecurity.services.UserService;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String users(Model model, Principal principal) {
        // Load user details for the currently logged-in user
        model.addAttribute("user",principal);
        principal.getName();
        return "index";
    }

    @PostMapping("/user")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @PostMapping("/users/deleteAll")
    public String deleteAllUsers() {
        userService.deleteAllUsers();
        return "redirect:/users";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        User existingUser = userService.getUserByUsername(user.getUsername());
        if (existingUser != null) {
            model.addAttribute("userExists", true);
            return "register";
        } else {
            userService.saveUser(user);
            model.addAttribute("successMessage", "Registration successful! You can now login.");
            return "redirect:/login";
        }
    }



//        if (userService.isUserExists(user.getUsername())) {
//            return "redirect:/login?error=usernameExists";
//        } else {
//            userService.saveUser(user);
//            model.addAttribute("successMessage", "Registration successful! You can now login.");
//            return "register";




    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user, Model model) {
        User loggedInUser = userService.getUserByusernameAndPassword(user.getUsername(), user.getPassword());
        if (loggedInUser != null) {
            return "redirect:/users"; // Redirect to the index page
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

}
