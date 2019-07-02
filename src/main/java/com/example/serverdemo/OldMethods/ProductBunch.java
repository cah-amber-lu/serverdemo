package com.example.serverdemo.OldMethods;

import java.net.URL;

public class ProductBunch {

    private Product[] products;

    public ProductBunch(int numNeeded, URL url) {
        products = new Product[numNeeded];

        Parser p = new Parser(url);

        for (int i = 0; i < numNeeded; i++) {
            products[i] = p.processObject();
            products[i].setId(i);
        }
    }

    public Product[] getProducts() {
        return products;
    }
}
