package com.exam.demo.model.dto;

import com.exam.demo.model.Item;

import java.util.List;

public class OrderResponseDto {

    private String orderRef;
    private List<ItemResponseDto> items;
    private String channel;
    private  String orderStatus;
    private  String storeName;

    public OrderResponseDto(String orderRef, List<ItemResponseDto> items, Integer quantity, String channel, String deliveryStatus, String orderStatus, String storeName) {
        this.orderRef = orderRef;
        this.items = items;
        this.channel = channel;
        this.orderStatus = orderStatus;
        this.storeName = storeName;
    }

    public OrderResponseDto(String orderRef, List<ItemResponseDto> items, String channel, String orderStatus, String storeName) {
        this.orderRef = orderRef;
        this.items = items;
        this.channel = channel;
        this.orderStatus = orderStatus;
        this.storeName = storeName;
    }

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

