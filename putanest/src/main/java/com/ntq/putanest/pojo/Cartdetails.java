package com.ntq.putanest.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cartdetails")
public class Cartdetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartDetailID")
    private Integer cartDetailId;

    @Column(name = "ProductID")
    private Integer productId;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "Price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "CartID", nullable = false)
    @JsonBackReference
    private Carts cart;

    public Integer getCartDetailId() {
        return this.cartDetailId;
    }

    public void setCartDetailId(Integer cartDetailId) {
        this.cartDetailId = cartDetailId;
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

    public Carts getCart() {
        return cart;
    }

    public void setCart(Carts cart) {
        this.cart = cart;
    }
}
