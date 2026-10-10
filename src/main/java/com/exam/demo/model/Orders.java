package com.exam.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class Orders {
    @Id
    private String orderRef;
    private String itemId;
    private Integer quantity;
    private String channel;
    private String deliveryStatus;

    public Orders(String orderRef, String itemId, Integer quantity, String channel, String deliveryStatus) {
        this.orderRef = orderRef;
        this.itemId = itemId;
        this.quantity = quantity;
        this.channel = channel;
        this.deliveryStatus = deliveryStatus;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}

