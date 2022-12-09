package com.wiley.BlogManagementSystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String showLoginForm() {
//        return "login";
//    }

    @GetMapping("login")
    public String showLogin(HttpServletRequest request){
        return "login";
    }
//    @PostMapping("login")
//    public String getLogin(HttpServletRequest request){
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        if (username.equals("admin")){
//            return "redirect:/adminDash";
//        } else {
//            return "redirect:/userDash";
//        }
//    }


    /*@GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }*/
}