package com.littlestore.littlestore.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.littlestore.littlestore.domain.Product;

public class ProductDTO implements Serializable {

    private Product product;

    public ProductDTO() {
        product = new Product();
    }

    public ProductDTO(Product product) {
        this.product = product;
    }

    @JsonIgnore
    public Product getProduct() {
        return product;
    }

    public Integer getId() {
        return product.getId();
    }

    public void setId(Integer id) {
        product.setId(id);
    }

    public String getSku() {
        return product.getSku();
    }

    public void setSku(String sku) {
        product.setSku(sku);
    }

    public String getName() {
        return product.getName();
    }

    public void setName(String name) {
        product.setName(name);
    }

    public String getDescription() {
        return product.getDescription();
    }

    public void setDescription(String description) {
        product.setDescription(description);
    }

    public BigDecimal getPrice() {
        return product.getPrice();
    }

    public void setPrice(BigDecimal price) {
        product.setPrice(price);
    }

    public Integer getQuantity() {
        return product.getQuantity();
    }

    public void setQuantity(Integer quantity) {
        product.setQuantity(quantity);
    }
}
