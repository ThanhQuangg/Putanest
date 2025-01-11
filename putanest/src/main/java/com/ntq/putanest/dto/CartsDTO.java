package com.ntq.putanest.dto;

import java.time.LocalDateTime;

public class CartsDTO {
    private Integer cartId;
    private Integer userId;
    private LocalDateTime createdAt;

    public Integer getCartId() {
        return this.cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
