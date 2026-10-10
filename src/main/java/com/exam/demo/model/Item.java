package com.exam.demo.model;

public class Item {
    private String itemId;
    private Integer quantity;
    private String deliveryStatus;

    public Item(String itemId, Integer quantity, String deliveryStatus) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.deliveryStatus = deliveryStatus;
    }

    public Item() {

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

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}