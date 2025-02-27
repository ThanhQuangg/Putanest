package com.ntq.putanest.pojo;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "guest_order_details")
public class GuestOrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "guest_order_id", nullable = false)
    private GuestOrders guestOrder;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public GuestOrders getGuestOrder() {
        return guestOrder;
    }

    public void setGuestOrder(GuestOrders guestOrder) {
        this.guestOrder = guestOrder;
    }
}

