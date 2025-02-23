package com.ntq.putanest.serviceimpl;

import com.ntq.putanest.dto.OrderdetailsDTO;
import com.ntq.putanest.pojo.Orderdetails;
import com.ntq.putanest.pojo.Products;
import com.ntq.putanest.repository.OrderDetailsRepository;
import com.ntq.putanest.repository.ProductsRepository;
import com.ntq.putanest.service.OrderDetailsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public List<OrderdetailsDTO> getAllOrderDetails() {
        return orderDetailsRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderdetailsDTO> getOrderDetailById(Integer orderDetailId) {
        return orderDetailsRepository.findById(orderDetailId).map(this::convertToDTO);
    }

    @Override
    public List<OrderdetailsDTO> getOrderDetailsByOrderId(Integer orderId) {
        return orderDetailsRepository.findByOrderId(orderId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Orderdetails saveOrderDetail(Orderdetails orderDetail) {
        return orderDetailsRepository.save(orderDetail);
    }

    // Chuyển đổi từ Orderdetails sang OrderdetailsDTO
    private OrderdetailsDTO convertToDTO(Orderdetails orderDetail) {
        OrderdetailsDTO dto = new OrderdetailsDTO();
        BeanUtils.copyProperties(orderDetail, dto);

        Integer productId = orderDetail.getProductId();
        if (productId != null) {
            Products product = productsRepository.findById(productId).orElse(null);
            if (product != null) {
                dto.setProductName(product.getProductName());
            }
        }
        return dto;
    }

    // Chuyển đổi từ OrderdetailsDTO sang Orderdetails
    private Orderdetails convertToEntity(OrderdetailsDTO dto) {
        Orderdetails orderDetail = new Orderdetails();
        BeanUtils.copyProperties(dto, orderDetail);
        if (dto.getProductId() != null) {
            Products product = new Products();
            product.setProductId(dto.getProductId());
            orderDetail.setProductId(dto.getProductId());
        }
        return orderDetail;
    }
}
