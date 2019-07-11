package com.example.serverdemo;

import com.example.serverdemo.OldMethods.ProductBunch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Controller
public class PrimaryController {

    private static final Logger LOG = LoggerFactory.getLogger(PrimaryController.class);

    private static final String LOG_FILE_PATH = "RequestLog.txt";

    private static final String PRODUCT_ID_PATH = "json/productid.txt";

    private List<String> productIds;

    @PostConstruct
    public void setup() throws IOException {
        if (new ClassPathResource(PRODUCT_ID_PATH).exists()) {
            productIds = Files.readAllLines(new ClassPathResource(PRODUCT_ID_PATH).getFile().toPath());
        }
        else {
            throw new RuntimeException("Cannot find " + PRODUCT_ID_PATH + " to read");
        }
    }

    @Autowired
    private TrizettoEndpoint te;

    @GetMapping("/listingTrizetto")
    public String listingTrizetto(Model model) throws IOException {
        writeToFile("Page 1");
        final long startTime = System.currentTimeMillis();
        List<Item> list = stringsToItems(productIds);
        final long endTime = System.currentTimeMillis();
        final long totalTime = endTime - startTime;
        model.addAttribute("products", list);
        model.addAttribute("time", totalTime);
        return "listingTrizetto";
    }

    @GetMapping("/listingTrizetto2")
    public String listingTrizetto2(Model model) throws IOException, NullPointerException {
        List<ApiResponse> body = te.listingTrizetto(new RequestWrapper(stringsToItems(productIds)));
        writeToFile("Page 2");
        model.addAttribute("body", body);
        return "listingTrizetto2";
    }

    @GetMapping("/listingTrizetto3")
    public String listingTrizetto3(Model model) throws IOException, NullPointerException {
        writeToFile("Page 3");
        model.addAttribute("products", stringsToItems(productIds));
        return "listingTrizetto3";
    }

    @GetMapping("/listingTrizetto4")
    public String listingTrizetto4(Model model)
            throws IOException, NullPointerException, InterruptedException, ExecutionException {
        writeToFile("Page 4");
        List<Item> list = stringsToItems(productIds);
        List<ApiResponse> responses = te.singleRequest(new RequestWrapper(list));
        model.addAttribute("body", responses);
        return "listingTrizetto4";

    }

    private void writeToFile(String name) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true));
        try {
            writer.append("--\r\n");
            writer.append(name).append(" made a call to the proxy at ")
                    .append(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        } finally {
            writer.close();
        }
    }

    /* Old, unused methods. */

    @GetMapping("/listing")
    public String listing(@RequestParam(name="name", required=false, defaultValue="User") String userName,
                          @RequestParam(name="productName", required=false, defaultValue="All") String productName,
                          @RequestParam(name="numLoaded", required=false, defaultValue="10") int numLoaded,
                          Model model) {

        model.addAttribute("name", userName);

        model.addAttribute("productName", productName);

        model.addAttribute("numLoaded", numLoaded);

        ProductBunch products = getProducts(numLoaded);

        model.addAttribute("products", products.getProducts());

        return "listing";
    }

    @GetMapping("/listing2")
    public String listing2(@RequestParam(name="name", required=false, defaultValue="User") String userName,
                           @RequestParam(name="productName", required=false, defaultValue="All") String productName,
                           @RequestParam(name="numLoaded", required=false, defaultValue="10") int numLoaded,
                           Model model) {


        model.addAttribute("name", userName);

        model.addAttribute("productName", productName);

        model.addAttribute("numLoaded", numLoaded);

        return "listing2";
    }

    @GetMapping("/listing3")
    public String listing3(Model model) {

        Random r = new Random();
        ArrayList<Integer> al = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            int id = r.nextInt(547) + 1;
            model.addAttribute("id" + i, id);
            al.add(id);
        }
        model.addAttribute("id", al);

        return "listing3";
    }

    private ProductBunch getProducts(int num) {
        URL url;
        try {
            url = new URL("http://api.icndb.com/jokes/random?exclude=explicit");
        } catch (MalformedURLException e) {
            System.out.println("Issue accessing the URL. Error message: " + e.getMessage());
            throw new RuntimeException("Cannot access products at this time.\n");
        }

        return new ProductBunch(num, url);
    }

    private List<Item> stringsToItems(List<String> pids) {
        List<Item> list = new ArrayList<>();
        for (String pid : pids) {
            String[] kvPair = pid.split(",");
            if (kvPair.length != 2) {
                throw new RuntimeException("Incorrect parsing of values.");
            }
            list.add(new Item(kvPair[0], kvPair[1]));
        }

        return list;
    }

}
