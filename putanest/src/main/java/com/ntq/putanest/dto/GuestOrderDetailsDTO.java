package com.ntq.putanest.dto;

import java.math.BigDecimal;

public class GuestOrderDetailsDTO {
    private Integer id;
    private Integer guestOrderId;
    private Integer productId;
    private Integer quantity;
    private BigDecimal price;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGuestOrderId() {
        return this.guestOrderId;
    }

    public void setGuestOrderId(Integer guestOrderId) {
        this.guestOrderId = guestOrderId;
    }

    public Integer getProductId() {
        return this.productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
