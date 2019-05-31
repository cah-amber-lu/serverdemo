package com.example.serverdemo;


import com.google.gson.Gson;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.*;


class CustomProxy{

    private RestTemplate restTemplate;

    public CustomProxy() {

        Proxy prx = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 1080));

        SimpleClientHttpRequestFactory rf = new SimpleClientHttpRequestFactory();

        rf.setProxy(prx);

        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "1080");

        restTemplate = new RestTemplate(rf);
    }



    public Product getAPI(int id) throws NullPointerException {

        String resourceURL
                = "http://api.icndb.com/jokes/";
        ResponseEntity<String> response
                = restTemplate.getForEntity(resourceURL + "/" + id, String.class);
        Gson gson = new Gson();
        Parser.Response pr = gson.fromJson(response.getBody(), Parser.Response.class);
//        Mono<Parser.Response> r = client.get()
//                .uri("http://api.icndb.com/jokes/" + id)
//                .retrieve()
//                .bodyToMono(Parser.Response.class);
//        Parser.Response pr = r.block();
        Product p = new Product();
        p.setName("Type name" + pr.type);
        p.setCost(Double.parseDouble(pr.value.id + 0.50));
        p.setContent(pr.value.joke.substring(0, 25));
        return p;
    }
}
