package com.ntq.putanest.controller;

import com.ntq.putanest.dto.OrderdetailsDTO;
import com.ntq.putanest.pojo.Orderdetails;
import com.ntq.putanest.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @GetMapping("/{orderId}")
    public ResponseEntity<List<OrderdetailsDTO>> getOrderDetailsByOrderId(@PathVariable Integer orderId) {
        List<OrderdetailsDTO> orderDetails = orderDetailsService.getOrderDetailsByOrderId(orderId);
        if (orderDetails.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderDetails);
    }
}