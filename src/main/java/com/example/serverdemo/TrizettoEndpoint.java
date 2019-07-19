package com.example.serverdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * A rest controller which calls the Trizetto API and processes the information. The data should be received when
 * the browser calls a certain subdomain.
 */
@RestController
public class TrizettoEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(TrizettoEndpoint.class);

    private static final String FILE_PATH = "json/blank.json";

    private static final String LOG_FILE_PATH = "resources/RequestLog.txt";

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

    private ExecutorService executorService;

    // Object for timekeeping.
    private ElapsedTime elapsedTime;

    @Value("classpath:json/blank.json")
    private Resource blankResource;

    @PostConstruct
    public void setup() throws IOException
    {
        // load json template at startup and keep in ram
        jsonTemplate = StreamUtils.copyToString(blankResource.getInputStream(), Charset.defaultCharset());
        // set-up headers
        headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(trizettoUsername, trizettoPassword);
        headers.setConnection("keep-alive");
        headers.setCacheControl("no-cache");
        headers.set("accept-encoding", "gzip, deflate");
        elapsedTime = new ElapsedTime();
        executorService = Executors.newFixedThreadPool(6);
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

        if (writeTimeToFile() == 0) {
            LOG.debug("Error writing into file");
            LOG.debug("Total time: From {} to {} for {}", elapsedTime.getStartTime(),
                    elapsedTime.getEndTime(), elapsedTime.getTotalTime());
        }

        return ar != null ? Arrays.asList(ar) : Collections.emptyList();
    }

    /**
     * Sends requests to the Trizetto API as single products in parallel.
     * @param wrapper: A requestwrapper object, which holds a list which wraps a ApiRequest
     * @return An ApiResponse wrapped in a list
     * @throws IOException if there is an error when opening or writing to a file
     * @throws ExecutionException if one of the parallel calls is aborted
     * @throws InterruptedException if the thread is interrupted
     */
    @PostMapping("/singleRequestCall")
    public List<ApiResponse> singleRequest (@RequestBody RequestWrapper wrapper)
            throws IOException, ExecutionException, InterruptedException {

        elapsedTime.setStartTime(System.currentTimeMillis());

        List<ApiResponse> responses = new ArrayList<>();

        List<Future<ApiResponse[]>> futures = new ArrayList<>();

        for (Item item : wrapper.getList()) {
            futures.add(callAPI(item));
        }

        // Blocking (?) get
        for (Future<ApiResponse[]> f : futures) {
            f.get();
        }

        for (Future<ApiResponse[]> f : futures) {
            if (f.isDone()) {
                responses.add(f.get()[0]);
            }
        }


        elapsedTime.setEndTime(System.currentTimeMillis());
        elapsedTime.setTotalTime(elapsedTime.getEndTime() - elapsedTime.getStartTime());

        if (writeTimeToFile() == 0) {
            LOG.debug("Error writing into file");
            LOG.debug("Total time: From {} to {} for {}", elapsedTime.getStartTime(),
                    elapsedTime.getEndTime(), elapsedTime.getTotalTime());
        }
        return responses;
    }

    private Future<ApiResponse[]> callAPI (Item item) throws IOException {
        List<ApiRequest> request = new ApiRequest().parse(jsonTemplate);
        return executorService.submit(() -> {
            List<ApiRequest.ServiceLine> line = new ArrayList<>();
            line.add(new ApiRequest.ServiceLine(item.getProcedureCode(), item.getItemNumber()));
            request.get(0).setLines(line);
            HttpEntity<List<ApiRequest>> entity = new HttpEntity<>(request, headers);
            ApiResponse[] ar = restTemplate.postForObject(trizettoUrl, entity, ApiResponse[].class);
            // Returns a blank APIResponse object if the call fails.
            if (ar == null) {
                ar = new ApiResponse[1];
                ar[0] = new ApiResponse();
            }
            return ar;
        });
    }

    private int writeTimeToFile() {
        if (!new File(LOG_FILE_PATH).exists() || !new File(LOG_FILE_PATH).canWrite()) {
            LOG.debug("Cannot write to file");
            return 0;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))){
            String time = elapsedTime.getTotalTime() + " ms";
            writer.append("\r\nTotal time taken: ").append(time).append("\r\n");
            return 1;
        } catch (IOException e){
            LOG.debug("Error writing in file: " + e.getLocalizedMessage());
            return 0;
        }
    }

}
