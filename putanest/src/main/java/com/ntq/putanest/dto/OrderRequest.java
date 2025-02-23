package com.ntq.putanest.dto;

import java.util.List;

public class OrderRequest {
    private Integer userId;
    private List<OrderDetailRequest> orderDetails;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<OrderDetailRequest> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailRequest> orderDetails) {
        this.orderDetails = orderDetails;
    }

    // Getters và Setters
}
