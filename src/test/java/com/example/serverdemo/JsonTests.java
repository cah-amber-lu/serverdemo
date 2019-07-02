package com.example.serverdemo;

import com.example.serverdemo.OldMethods.Parser;
import com.example.serverdemo.OldMethods.Product;
import com.example.serverdemo.OldMethods.ProductBunch;
import com.google.gson.Gson;
import org.apache.tomcat.util.buf.UriUtil;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.Request;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Wrapper;
import java.util.*;


public class JsonTests {

    @Test
    public void singleCall() throws IOException, NullPointerException {
        File file = new ClassPathResource("json/productid.txt").getFile();
        String append = new String(Files.readAllBytes(file.toPath()));
        // Map<String, String> map = new HashMap<>();

        List<String[]> list = new ArrayList<>();
        String[] split = append.split("\\r?\\n");

        for (String s : split) {
            String[] kvPair = s.split(",");
            if (kvPair.length != 2) {
                throw new RuntimeException("Incorrect parsing of values.");
            }
            list.add(kvPair);
        }

        TrizettoEndpoint te = new TrizettoEndpoint();

        List<ApiResponse> list2 = te.singleRequest("OJCMMM", "E1399");

        System.out.println(list2.size());

    }

    @Test
    public void callThroughRT() throws IOException, NullPointerException {
        RestTemplate restTemplate = new RestTemplate();

        File file = new ClassPathResource("json/productid.txt").getFile();
        String append = new String(Files.readAllBytes(file.toPath()));
        // Map<String, String> map = new HashMap<>();

        List<Item> list = new ArrayList<>();
        String[] split = append.split("\\r?\\n");

        for (String s : split) {
            String[] kvPair = s.split(",");
            if (kvPair.length != 2) {
                throw new RuntimeException("Incorrect parsing of values.");
            }
            list.add(new Item(kvPair[0], kvPair[1]));
        }

        RequestWrapper rw = new RequestWrapper(list);

        System.out.println(rw);

        for (Item r : rw.getList()) {
            System.out.println(r.getItemNumber() + " " + r.getProcedureCode());
        }

    ApiResponse[] body = restTemplate.postForObject("http://localhost:8080/trizettoCall/",
                rw,
                ApiResponse[].class);

        for (ApiResponse a : body) {
            Assert.assertNull(a.getError());
            System.out.println(a.getLines().get(0).getProcedureCode());
            System.out.println(a.getLines().get(0).getItemNumber());
        }


    }


    @Test
    public void directCall() throws IOException {
        File file = new ClassPathResource("json/productid.txt").getFile();
        String append = new String(Files.readAllBytes(file.toPath()));
        // Map<String, String> map = new HashMap<>();

        List<Item> list = new ArrayList<>();
        String[] split = append.split("\\r?\\n");

        for (String s : split) {
            String[] kvPair = s.split(",");
            if (kvPair.length != 2) {
                throw new RuntimeException("Incorrect parsing of values.");
            }
            list.add(new Item(kvPair[0], kvPair[1]));
        }

        TrizettoEndpoint te = new TrizettoEndpoint();

        RequestWrapper rw = new RequestWrapper(list);

        List<ApiResponse> list2 = te.listingTrizetto(rw);

        System.out.println(new RequestWrapper(list));

        System.out.println(UriUtils.encode(new RequestWrapper(list).toString(), "UTF-8"));

        String rwString = new RequestWrapper(list).toString();

        System.out.println(UriUtils.decode(rwString, "UTF-8"));

        System.out.println(list2.size());
        for (ApiResponse a : list2) {
            Assert.assertNull(a.getError());
            System.out.println(a.getLines().get(0).getProcedureCode());
            System.out.println(a.getLines().get(0).getItemNumber());
        }
    }
}
