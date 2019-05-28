package com.example.serverdemo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PrimaryController {

    @GetMapping("/listing")
    public String listing(@RequestParam(name="name", required=false, defaultValue="User") String userName,
                          @RequestParam(name="productName", required=false, defaultValue="All") String productName,
                          @RequestParam(name="numLoaded", required=false, defaultValue="10") String numLoaded,
                          Model model) {


        model.addAttribute("name", userName);

        model.addAttribute("productName", productName);

        model.addAttribute("numLoaded", numLoaded);

        ProductBunch products = getProducts(Integer.parseInt(numLoaded));

        model.addAttribute("products", products.getProducts());

        return "listing";
    }

    @GetMapping("/listing2")
    public String listing2(@RequestParam(name="name", required=false, defaultValue="User") String userName,
                           @RequestParam(name="productName", required=false, defaultValue="All") String productName,
                           @RequestParam(name="numLoaded", required=false, defaultValue="10") String numLoaded,
                           Model model) {


        model.addAttribute("name", userName);

        model.addAttribute("productName", productName);

        model.addAttribute("numLoaded", numLoaded);

        return "listing2";
    }

    /**
     * Get a random number of products between 2 and 10.
     * URL is set to the Chuck Norris jokes API database.
     */
    private ProductBunch getProducts(int num) {
        URL url;
        try {
            url = new URL("http://api.icndb.com/jokes/random?exclude=explicit");
        } catch (MalformedURLException e) {
            System.out.println("Issue accessing the URL. Error message: " + e.getMessage());
            throw new RuntimeException("Cannot access products at this time.\n");
        }
//        Random random = new Random();
//        int numProducts = random.nextInt(9) + 2;
        return new ProductBunch(num, url);
    }

}
