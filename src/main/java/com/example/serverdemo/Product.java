package com.example.serverdemo;

public class Product {
    private String name;
    private String imageURL;
    private int id;
    private double cost;

    /***
     * Creates a product object, which stores information about a product.
     * @param name: The name of the product.
     * @param imageURL: A URL (either local/web) which links to an image.
     * @param id: The unique ID of the product.
     * @param cost: Cost of the product.
     */
    public Product(String name, String imageURL, int id, double cost) {
        this.name = name;
        this.imageURL = imageURL;
        this.id = id;
        this.cost = cost;
    }

    /***
     * A default Product for when loading a product caused an error.
     */
    public Product() {
        this.name = "Placeholder";
        this.imageURL = "";
        this.id = -1;
        this.cost = -1.0;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getId() {
        return id;
    }

    public double getCost() {
        return cost;
    }
}
