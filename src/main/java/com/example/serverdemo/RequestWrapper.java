package com.example.serverdemo;

import java.util.ArrayList;
import java.util.List;

public class RequestWrapper  {

    private List<Item> items;

    public RequestWrapper() {}

    public RequestWrapper (List<Item> items) {
        this.items = items;
    }

    public List<Item> getList() {
        return items;
    }

    public void setList(List<Item> list) {
        this.items = list;
    }
}

