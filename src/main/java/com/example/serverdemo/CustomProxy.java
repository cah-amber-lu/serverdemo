package com.example.serverdemo;


import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

class CustomProxy{

    private WebClient client = WebClient.create("localhost:9080");

    public Product getAPI(int id) throws NullPointerException {
        Mono<Parser.Response> r = client.get()
                .uri("http://api.icndb.com/jokes/" + id)
                .retrieve()
                .bodyToMono(Parser.Response.class);
        Parser.Response pr = r.block();
        Product p = new Product();
        p.setName("Type name" + pr.type);
        p.setCost(Double.parseDouble(pr.value.id + 0.50));
        p.setContent(pr.value.joke.substring(0, 25));
        return p;
    }
}
