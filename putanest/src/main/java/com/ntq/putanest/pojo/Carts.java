package com.ntq.putanest.pojo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Carts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartID")
    private Integer cartId;

    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Cartdetails> cartDetails;

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

    public List<Cartdetails> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<Cartdetails> cartDetails) {
        this.cartDetails = cartDetails;
    }
}
