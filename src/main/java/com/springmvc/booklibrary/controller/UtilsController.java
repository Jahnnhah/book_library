package com.springmvc.booklibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UtilsController {

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

}
