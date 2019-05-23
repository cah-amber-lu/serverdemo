package com.example.serverdemo;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Parser {

    private URL url;


    static class Response {
        String type;
        Value value;
        String categories;
    }

    static class Value {
        String id;
        String joke;
    }

    public Parser(URL url) {
        this.url = url;
    }


    public Product processObject() {
        Product p = new Product();
        Gson gson = new Gson();
        Response r = gson.fromJson(readURL(), Response.class);
        p.setName("Test name " + r.type);
        p.setContent(r.value.joke);
        p.setId(Integer.parseInt(r.value.id));
        p.setCost(Integer.parseInt(r.value.id) + 0.50);
        return p;
    }

    public String readURL() {
        String raw;
        URLConnection conn;
        try {
            conn = this.url.openConnection();
        } catch(IOException e) {
            System.out.println("Error when connecting to API: " + e.getMessage());
            throw new RuntimeException("IO error");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                StandardCharsets.UTF_8))) {
            raw = reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            System.out.println("Error when reading text: " + e.getMessage());
            throw new RuntimeException("IO error");
        }
        return raw;
    }

}
