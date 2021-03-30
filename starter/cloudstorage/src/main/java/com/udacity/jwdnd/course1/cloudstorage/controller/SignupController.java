package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("signup")
public class SignupController {

    UserService userService;
//INSTANTIATE 'userService'
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signUpView(){
        return "signup";
    }
    @PostMapping
    public String signUpUser(@ModelAttribute User user, Model model){
        //THIS STRING NAME 'signupError' SHOULD BE PRESENT IN THE 'signup.html' TO BE INJECTED BY THYMELEAF TO THE VIEW
        String signupError = null;
        //IF USER ALREADY EXISTS
        if (userService.usernameExists(user.getUsername())){
            signupError = "Username already exists, please choose another one";
        }
        if (signupError == null) {
            User createdUser = userService.createUser(user);
            System.out.println(createdUser.toString());
            if (createdUser == null) {
                signupError = "There was an error creating the account";
            }
            else {
                model.addAttribute("signupSuccess", true);
                return  "redirect:/login?signupSuccess";
            }
        }
        else {
            model.addAttribute("signupError", signupError);
        }


        return "signup";
    }
}
