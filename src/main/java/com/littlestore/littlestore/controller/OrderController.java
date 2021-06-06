package com.littlestore.littlestore.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.littlestore.littlestore.domain.dto.IdDTO;
import com.littlestore.littlestore.domain.dto.OrderDTO;
import com.littlestore.littlestore.service.OrderService;

@RestController
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/v1/orders/{id}")
    public OrderDTO findById(@PathVariable Integer id) {
        return new OrderDTO(orderService.findById(id));
    }

    @GetMapping("/v1/orders")
    public List<OrderDTO> findOrders() {
        return orderService.findAll().stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/v1/orders/{id}")
    public void update(@PathVariable Integer id, @RequestBody OrderDTO orderDTO) {
        orderDTO.setId(id);
        orderService.update(orderDTO.getOrder());
    }

    @PostMapping("/v1/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public IdDTO create(@RequestBody OrderDTO orderDTO) {
        return new IdDTO(orderService.create(orderDTO.getOrder()));
    }

    @DeleteMapping("/v1/orders/{id}")
    public void update(@PathVariable Integer id) {
        orderService.delete(id);
    }
}
