package com.coursework.firmclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coursework.firmclient.entity.User;
import com.coursework.firmclient.exception.UserNotFoundException;
import com.coursework.firmclient.form.UserForm;
import com.coursework.firmclient.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signin")
    public String getSignInForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "signin";
    }

    @PostMapping("/check_auth")
    public String authUser(@ModelAttribute UserForm userForm) {
        try {
            userService.signIn(new User(userForm.getUsername(), userForm.getPassword()));
        } catch (UserNotFoundException userNotFoundException) {
            return "error-user-not-found";
        }
        return "redirect:/";
    }
}
