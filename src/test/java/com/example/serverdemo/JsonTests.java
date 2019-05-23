package com.example.serverdemo;

import org.junit.Test;
import org.junit.Assert;

import java.net.MalformedURLException;
import java.net.URL;


public class JsonTests {

    /**
     * Basic test with set joke URLs.
     */
    @Test
    public void testAccuracy(){
        Parser p;
        try {
            p = new Parser(new URL("http://api.icndb.com/jokes/52"));
            Product pr = p.processObject();
            Assert.assertEquals("Test name success", pr.getName());
            Assert.assertEquals("When Chuck Norris sends in his taxes, he sends blank forms and includes " +
                    "only a picture of himself, crouched and ready to attack. Chuck Norris has not had to pay " +
                    "taxes, ever.", pr.getContent());
            Assert.assertEquals(52, pr.getId());
            Assert.assertEquals(52.50, pr.getCost(), 0);
        } catch (MalformedURLException e) {
            System.out.println("Failed Test #1");
        }
        try {
            p = new Parser(new URL("http://api.icndb.com/jokes/531"));
            Product pr = p.processObject();
            Assert.assertEquals("Test name success", pr.getName());
            Assert.assertEquals("Jesus can walk on water, but Chuck Norris can swim through land.",
                    pr.getContent());
            Assert.assertEquals(531, pr.getId());
            Assert.assertEquals(531.50, pr.getCost(), 0);
        } catch (MalformedURLException e) {
            System.out.println("Failed Test #2");
        }
    }

    /**
     * Test with an entire product bunch. Requires manual verification.
     */
    @Test
    public void batchTest() {
        ProductBunch pb;
        try {
            pb = new ProductBunch(5,
                    new URL("http://api.icndb.com/jokes/random?exclude=explicit"));
            for (Product p : pb.getProducts()) {
                Assert.assertEquals("Test name success", p.getName());
                System.out.println("Product #" + p.getId());
                System.out.println("Content: " + p.getContent());
                System.out.println("Cost: $" + p.getCost());
                System.out.println("===========\n");
            }

        } catch (MalformedURLException e) {
            System.out.println("Failed batch test");
        }


    }
}
