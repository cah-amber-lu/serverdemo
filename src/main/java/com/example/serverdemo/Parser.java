package com.example.serverdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Parser {

    URL url;


    static class Value {

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
