package com.example.serverdemo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jdk.nashorn.internal.objects.NativeJSON;
import net.minidev.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProxyEndpoint {

    @GetMapping("/endpoint")
    public String midpoint(@RequestParam(name="id")String id,
                           Model model) {

        RestTemplate restTemplate = new RestTemplate();

        String resourceURL
                = "http://api.icndb.com/jokes/";
        ResponseEntity<String> response
                = restTemplate.getForEntity(resourceURL + id, String.class);

        Gson gson = new Gson();

        Response pr = gson.fromJson(response.getBody(), Response.class);

        ArrayList<String> l = new ArrayList<>();

        l.add("Type name " + pr.type);

        l.add(pr.value.joke.substring(0, 30));

        l.add(pr.value.id + 0.50);

        return gson.toJson(l);
    }

    static class Response {
        String type;
        Value value;
        String categories;
    }

    static class Value {
        String id;
        String joke;
    }
}
