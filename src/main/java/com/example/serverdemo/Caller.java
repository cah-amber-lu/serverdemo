package com.example.serverdemo;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@RestController
public class Caller {

    @GetMapping("/listing3")
    public void generateID(Model model) {
        Random r = new Random();
        for (int i = 0; i < 12; i++) {
            int id = r.nextInt(547) + 1;
            model.addAttribute("id" + i, id);
        }
    }

}
