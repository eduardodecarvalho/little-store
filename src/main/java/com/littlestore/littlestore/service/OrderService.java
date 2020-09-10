package com.littlestore.littlestore.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littlestore.littlestore.domain.Order;
import com.littlestore.littlestore.domain.OrderedItem;
import com.littlestore.littlestore.repository.OrderRepository;
import com.littlestore.littlestore.utils.BusinessException;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private ProductService productService;

    private ClientService clientService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductService productService, ClientService clientService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.clientService = clientService;
    }

    public Order findById(Integer id) {
        return orderRepository.findById(id).orElseThrow(() -> new BusinessException(BusinessException.CLIENT_NOT_FOUND));
    }

    public List<Order> findAll() {
        return orderRepository.findAll().stream()
                .sorted(Comparator.comparing(Order::getPurchaseDate).reversed()).collect(Collectors.toList());
    }

    @Transactional
    public void update(Order order) {
        if (orderRepository.existsById(order.getId())) {
            save(order);
        }
    }

    public Integer create(Order order) {
        save(order);
        return order.getId();
    }

    public void delete(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new BusinessException(BusinessException.CLIENT_NOT_FOUND));
        orderRepository.delete(order);
    }

    private void save(Order order) {
        buildOrder(order);
        order.getOrderedItems().stream().forEach(item -> item.setOrder(order));
        orderRepository.save(order);
    }

    private void buildOrder(Order order) {
        BigDecimal totalValue = BigDecimal.ZERO;
        order.setPurchaseDate(LocalDateTime.now());
        for (OrderedItem item : order.getOrderedItems()) {
            productService.withdrawFromStock(item);
            BigDecimal itemCost = item.getTotalPrice();
            totalValue = totalValue.add(itemCost);
        }
        order.setPurchaseValue(totalValue);
    }
}
