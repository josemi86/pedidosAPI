package com.exam.demo.model.dto;

public class ItemResponseDto {
    private String itemId;
    private Integer quantity;
    private String deliveryStatus;
    private String displayName;

    public ItemResponseDto(String itemId, Integer quantity, String deliveryStatus) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.deliveryStatus = deliveryStatus;
    }

    public ItemResponseDto() {

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
