package com.ntq.putanest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrdersDTO {
    private Integer orderId;
    private Integer userId;
    private BigDecimal totalAmount;
    private String orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Integer getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
