package com.example.serverdemo.OldMethods;

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

    /**
     * A class to store the response.
     */
    static class Response {
        String type;
        Value value;
        String categories;
    }

    /**
     * A class to store the value.
     */
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
        /* Truncates the string if it's too long so it looks better. */
        p.setContent(r.value.joke.substring(0, Math.min(r.value.joke.length(), 30)));
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
