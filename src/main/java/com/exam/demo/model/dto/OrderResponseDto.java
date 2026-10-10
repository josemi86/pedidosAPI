package com.exam.demo.model.dto;

import java.util.List;

public class OrderResponseDto {

    private String orderRef;
    private List<ItemResponseDto> items;
    private String channel;
    private  String orderStatus;
    private  String storeName;

    public OrderResponseDto() {

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

    public List<ItemResponseDto> getItems() {
        return items;
    }

    public void setItems(List<ItemResponseDto> items) {
        this.items = items;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

}

