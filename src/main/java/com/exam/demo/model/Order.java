package com.exam.demo.model;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Order {

    private String orderRef;
    private List<Item> items;
    private String channel;

    public Order(String orderRef, List<Item> items, String channel) {
        this.orderRef = orderRef;
        this.items = items;
        this.channel = channel;
    }

    public Order() {

    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}

