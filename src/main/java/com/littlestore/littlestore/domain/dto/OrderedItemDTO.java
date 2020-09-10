package com.littlestore.littlestore.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.littlestore.littlestore.domain.OrderedItem;

public class OrderedItemDTO implements Serializable {

    private OrderedItem orderedItem;

    public OrderedItemDTO() {
        orderedItem = new OrderedItem();
    }

    public OrderedItemDTO(OrderedItem orderedItem) {
        this.orderedItem = orderedItem;
    }

    @JsonIgnore
    public OrderedItem getOrderedItem() {
        return orderedItem;
    }

    public Integer getId() {
        return orderedItem.getId();
    }

    public void setId(Integer id) {
        orderedItem.setId(id);
    }

    public BigDecimal getTotalPrice() {
        return orderedItem.getTotalPrice();
    }

    public Integer getQuantity() {
        return orderedItem.getQuantity();
    }

    public void setQuantity(Integer quantity) {
        orderedItem.setQuantity(quantity == null ? 0 : quantity);
    }

    public ProductDTO getProduct() {
        return new ProductDTO(orderedItem.getProduct());
    }

    public void setProduct(ProductDTO productDTO) {
        orderedItem.setProduct(productDTO.getProduct());
    }
}
