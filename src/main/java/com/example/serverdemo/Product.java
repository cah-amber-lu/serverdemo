package com.example.serverdemo;

public class Product {
    private String name;
    private String content;
    private int id;
    private double cost;

    /***
     * Creates a product object, which stores information about a product.
     * @param name: The name of the product.
     * @param content: A URL (either local/web) which links to an image.
     * @param id: The unique ID of the product.
     * @param cost: Cost of the product.
     */
    public Product(String name, String content, int id, double cost) {
        this.name = name;
        this.content = content;
        this.id = id;
        this.cost = cost;
    }

    /***
     * A default Product for when loading a product caused an error.
     */
    public Product() {
        this.name = "Placeholder";
        this.content = "";
        this.id = -1;
        this.cost = -1.0;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }

    public double getCost() {
        return cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
