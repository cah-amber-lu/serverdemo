package com.example.serverdemo;


import org.junit.Test;
import org.junit.Assert;
import org.springframework.core.io.ClassPathResource;

import javax.validation.constraints.AssertTrue;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class ProxyTest {

    @Test
    public void ReadAndParseFile() throws IOException {

    }

    @Test
    public void TestMultipleRequest() throws IOException {
        File resource = new ClassPathResource("json/multiple.json").getFile();
        Assert.assertTrue(resource.canRead());
        System.out.println(new ApiRequest().parse(resource).size());
//        List<ApiResponse> ar = new TrizettoEndpoint().listingTrizetto("json/multiple.json");
//        for (ApiResponse a : ar) {
//            System.out.println(a.getTaxId());
//            Assert.assertNull(a.getError());
//        }
    }



}
