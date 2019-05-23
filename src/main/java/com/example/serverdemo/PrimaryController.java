package com.example.serverdemo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PrimaryController {

    @GetMapping("/index")
    public String index(@RequestParam(name="name", required=false, defaultValue="User") String userName, Model model) {
        model.addAttribute("name", userName);
        return "index";
    }


}
