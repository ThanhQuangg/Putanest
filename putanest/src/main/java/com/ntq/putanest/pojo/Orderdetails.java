package com.ntq.putanest.pojo;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "orderdetails")
public class Orderdetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderDetailID")
    private Integer orderDetailId;

    @Column(name = "OrderID", insertable = false, updatable = false)
    private Integer orderId;

    @Column(name = "ProductID")
    private Integer productId;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "Price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "OrderID", nullable = false)
    private Orders order;


    public Integer getOrderDetailId() {
        return this.orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }
}
