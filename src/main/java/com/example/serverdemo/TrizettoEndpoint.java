package com.example.serverdemo;

import jdk.nashorn.internal.objects.annotations.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * A rest controller which calls the Trizetto API and processes the information. The data should be received when
 * the browser calls a certain subdomain.
 */
@RestController
public class TrizettoEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(TrizettoEndpoint.class);

    @Value("${serverdemo.trizetto.url}")
    public String trizettoUrl;

    @Value("${serverdemo.trizetto.username}")
    public String trizettoUsername;

    @Value("${serverdemo.trizetto.password}")
    public String trizettoPassword;

    private RestTemplate restTemplate = new RestTemplate();


    /**
     *
     * This function assumes that there is already existing plan information (currently represented with
     * default.json). It receives a list of product ID numbers, which can then be used to make calls to Trizetto.
     * @param wrapper: List of <item number (unique), product code (nonunique)> of the product.
     * @return the response from the server as a POJO.
     * @throws IOException if the base json file cannot be read.
     */
    @PostMapping("/trizettoCall")
    public List<ApiResponse> listingTrizetto (@RequestBody RequestWrapper wrapper)
            throws IOException  {

        HttpHeaders headers = setHeaders();

        LOG.info("Calling function");

        LOG.debug("URL is " + trizettoUrl);
        LOG.debug("UN is " + trizettoUsername);
        LOG.debug("PW is " + trizettoPassword);

        String filePath = "json/blank.json";

        File resource = new ClassPathResource(filePath).getFile();

        List<ApiRequest> request = new ApiRequest().parse(resource);

        List<ApiRequest.ServiceLine> services = new ArrayList<>();

        for (Item s : wrapper.getList()) {
            ApiRequest.ServiceLine service = new ApiRequest.ServiceLine(s.getProcedureCode(), s.getItemNumber());
            services.add(service);
        }

        request.get(0).setLines(services);

        HttpEntity<List<ApiRequest>> entity = new HttpEntity<>(request, headers);

        ApiResponse[] ar = restTemplate
                .exchange(trizettoUrl, HttpMethod.POST, entity, ApiResponse[].class)
                .getBody();

//        LOG.info("request done: {}", ar);
        LOG.info("number of responses: {}", ar.length);

        return ar != null ? Arrays.asList(ar) : Collections.emptyList();
    }


//    @PostMapping("/singleRequestCall")
//    public List<ApiResponse> singleRequest (@RequestParam String itemNumber, @RequestParam String productCode)
//            throws IOException {
//        List<Item> temp = new ArrayList<>();
//        temp.add(new Item(itemNumber, productCode));
//        RequestWrapper rw = new RequestWrapper(temp);
//        LOG.info(rw.getList().get(0).getItemNumber() + " " + rw.getList().get(0).getProcedureCode());
//        return listingTrizetto(rw);
//    }

    @PostMapping("/singleRequestCall")
    public List<ApiResponse> singleRequest (@RequestBody RequestWrapper wrapper)
            throws IOException {

        HttpHeaders headers = setHeaders();

        String filePath = "json/blank.json";

        File resource = new ClassPathResource(filePath).getFile();

        List<ApiRequest> request = new ApiRequest().parse(resource);

        List<ApiResponse> responses = new ArrayList<>();

        for (Item item : wrapper.getList()) {
            List<ApiRequest.ServiceLine> line = new ArrayList<>();
            line.add(new ApiRequest.ServiceLine(item.getProcedureCode(), item.getItemNumber()));
            request.get(0).setLines(line);
            HttpEntity<List<ApiRequest>> entity = new HttpEntity<>(request, headers);
            ApiResponse[] ar = restTemplate
                    .exchange(trizettoUrl, HttpMethod.POST, entity, ApiResponse[].class)
                    .getBody();

            responses.add(ar[0]);
        }
        return responses;
    }

    private HttpHeaders setHeaders() {

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.setBasicAuth(trizettoUsername, trizettoPassword);

        headers.setConnection("keep-alive");

        headers.setCacheControl("no-cache");

        headers.set("accept-encoding", "gzip, deflate");

        return headers;
    }

}