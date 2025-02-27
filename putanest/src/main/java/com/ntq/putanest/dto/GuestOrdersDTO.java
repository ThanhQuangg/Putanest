package com.ntq.putanest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class GuestOrdersDTO {
    private Integer guestOrderId;
    private String orderCode;
    private String customerName;
    private String phoneNumber;
    private String email;
    private String address;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<GuestOrderDetailsDTO> guestOrderDetails;

    public Integer getGuestOrderId() {
        return this.guestOrderId;
    }

    public void setGuestOrderId(Integer guestOrderId) {
        this.guestOrderId = guestOrderId;
    }

    public String getOrderCode() {
        return this.orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<GuestOrderDetailsDTO> getGuestOrderDetails() {
        return guestOrderDetails;
    }

    public void setGuestOrderDetails(List<GuestOrderDetailsDTO> guestOrderDetails) {
        this.guestOrderDetails = guestOrderDetails;
    }
}
