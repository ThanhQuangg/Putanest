package com.ntq.putanest.dto;

import java.math.BigDecimal;

public class CartdetailsDTO {
    private Integer cartDetailId;
    private Integer cartId;
    private Integer productId;
    private Integer quantity;
    private BigDecimal price;
    private String productName;

    public Integer getCartDetailId() {
        return this.cartDetailId;
    }

    public void setCartDetailId(Integer cartDetailId) {
        this.cartDetailId = cartDetailId;
    }

    public Integer getCartId() {
        return this.cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
