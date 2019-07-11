package com.example.serverdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;


/**
 * A rest controller which calls the Trizetto API and processes the information. The data should be received when
 * the browser calls a certain subdomain.
 */
@RestController
public class TrizettoEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(TrizettoEndpoint.class);

    private static final String FILE_PATH = "json/blank.json";

    @Value("${serverdemo.trizetto.url}")
    public String trizettoUrl;

    @Value("${serverdemo.trizetto.username}")
    public String trizettoUsername;

    @Value("${serverdemo.trizetto.password}")
    public String trizettoPassword;

    @Autowired
    private RestTemplate restTemplate;

    private HttpHeaders headers;

    private String jsonTemplate;

    /** Variables for timekeeping. */

    ElapsedTime elapsedTime;


    @PostConstruct
    public void setup() throws IOException
    {
        // load json template at startup and keep in ram
        jsonTemplate = new String(Files.readAllBytes(new ClassPathResource(FILE_PATH).getFile().toPath()));
        headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(trizettoUsername, trizettoPassword);
        headers.setConnection("keep-alive");
        headers.setCacheControl("no-cache");
        headers.set("accept-encoding", "gzip, deflate");
        elapsedTime = new ElapsedTime();
    }

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

        LOG.info("Calling function");

        LOG.debug("URL={}, UN={}, PW={}", trizettoUrl, trizettoUsername, trizettoPassword);

        elapsedTime.setStartTime(System.currentTimeMillis());

        List<ApiRequest> request = new ApiRequest().parse(jsonTemplate);

        List<ApiRequest.ServiceLine> services = new ArrayList<>();

        for (Item s : wrapper.getList()) {
            ApiRequest.ServiceLine service = new ApiRequest.ServiceLine(s.getProcedureCode(), s.getItemNumber());
            services.add(service);
        }

        request.get(0).setLines(services);

        HttpEntity<List<ApiRequest>> entity = new HttpEntity<>(request, headers);

        ApiResponse[] ar = restTemplate.postForObject(trizettoUrl, entity, ApiResponse[].class);

        elapsedTime.setEndTime(System.currentTimeMillis());

        elapsedTime.setTotalTime(elapsedTime.getEndTime() - elapsedTime.getStartTime());

        LOG.info("Total time: From {} to {} for {}", elapsedTime.getStartTime(),
                elapsedTime.getEndTime(), elapsedTime.getTotalTime());

        // LOG.info("number of responses: {}", ar.length);

        return ar != null ? Arrays.asList(ar) : Collections.emptyList();
    }

    @PostMapping("/singleRequestCall")
    public List<ApiResponse> singleRequest (@RequestBody RequestWrapper wrapper)
            throws IOException {

        elapsedTime.setStartTime(System.currentTimeMillis());

        List<ApiRequest> request = new ApiRequest().parse(jsonTemplate);

        List<ApiResponse> responses = new ArrayList<>();

        IntStream.range(0, wrapper.getList().size()).parallel().forEach(i -> {
            Item item = wrapper.getList().get(i);
            List<ApiRequest.ServiceLine> line = new ArrayList<>();
            line.add(new ApiRequest.ServiceLine(item.getProcedureCode(), item.getItemNumber()));
            request.get(0).setLines(line);

            HttpEntity<List<ApiRequest>> entity = new HttpEntity<>(request, headers);
            ApiResponse[] ar = restTemplate.postForObject(trizettoUrl, entity, ApiResponse[].class);
            responses.add(ar[0]);
            elapsedTime.addIncrement(System.currentTimeMillis());
            LOG.info("Elapsed time {} ms", elapsedTime.getLastIncrement());
        });

        elapsedTime.setEndTime(System.currentTimeMillis());
        elapsedTime.setTotalTime(elapsedTime.getEndTime() - elapsedTime.getStartTime());

        LOG.info("Total time: From {} to {} for {}", elapsedTime.getStartTime(),
                elapsedTime.getEndTime(), elapsedTime.getTotalTime());

        return responses;
    }

}
