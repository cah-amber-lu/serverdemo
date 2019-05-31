package com.example.serverdemo;


import org.junit.Test;
import org.junit.Assert;

public class ProxyTest {

    @Test
    public void BasicProxyTest() {

        CustomProxy cp = new CustomProxy();

        Product p = cp.getAPI(170);

        Assert.assertEquals(p.getName(), "Test name success");

    }

}
