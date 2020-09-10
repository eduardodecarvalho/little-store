package com.littlestore.littlestore.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.littlestore.littlestore.domain.Order;

public class OrderDTO implements Serializable {

    private Order order;

    public OrderDTO() {
        order = new Order();
    }

    public OrderDTO(Order order) {
        this.order = order;
    }

    @JsonIgnore
    public Order getOrder() {
        return order;
    }

    public Integer getId() {
        return order.getId();
    }

    public void setId(Integer id) {
        order.setId(id);
    }

    public ClientDTO getClient() {
        return new ClientDTO(order.getClient());
    }

    public void setClient(ClientDTO clientDTO) {
        order.setClient(clientDTO.getClient());
    }

    public BigDecimal getPurchaseValue() {
        return order.getPurchaseValue();
    }

    public void setPurchaseValue(BigDecimal purchaseValue) {
        order.setPurchaseValue(purchaseValue);
    }

    public Date getPurchaseDate() {
        if (order.getPurchaseDate() != null) {
            return Date.from(order.getPurchaseDate().atZone(ZoneOffset.UTC).toInstant());
        }
        return null;
    }

    public void setPurchaseDate(Date purchaseDate) {
        if (purchaseDate != null) {
            order.setPurchaseDate(LocalDateTime.ofInstant(purchaseDate.toInstant(), ZoneId.systemDefault()));
        }
    }

    public List<OrderedItemDTO> getOrderedItems() {
        if (order.getOrderedItems() == null) {
            return Collections.emptyList();
        }
        return order.getOrderedItems().stream().map(OrderedItemDTO::new).collect(Collectors.toList());
    }

    public void setOrderedItems(List<OrderedItemDTO> orderedItems) {
        order.setOrderedItems(orderedItems.stream().map(OrderedItemDTO::getOrderedItem).collect(Collectors.toList()));
    }

}
