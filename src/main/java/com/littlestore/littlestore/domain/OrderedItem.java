package com.littlestore.littlestore.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ORDERED_ITEM")
public class OrderedItem {

    private Integer id;

    @Column(nullable = false)
    private int quantity;
    private Product product;
    private Order order;

    @ManyToOne()
    @JoinColumn(name = "id_client_order", nullable = false)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Transient
    public BigDecimal getTotalPrice() {
        if (product != null) {
            return getProduct().getPrice().multiply(BigDecimal.valueOf(getQuantity()));
        }
        return null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @ManyToOne()
    @JoinColumn(name = "id_product")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
