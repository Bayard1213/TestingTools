package xyz.towork.TestingTools.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;



@Controller
public class MainController {
    
    //GET
    @GetMapping("/")
    public String indexPage(Model model) {
        return "index";
    }
    @GetMapping("/json")
    public String jsonPage(Model model, HttpServletRequest request) {
        model.addAttribute("uri", request.getRequestURI());
        return "json";
    }


}
