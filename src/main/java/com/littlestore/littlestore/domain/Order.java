package com.littlestore.littlestore.domain;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "CLIENT_ORDER")
@SQLDelete(sql = "UPDATE CLIENT_ORDER SET DELETED = ID WHERE ID = ?")
@Where(clause = "DELETED = 0")
public class Order {

    private Integer id;
    private Client client;
    private BigDecimal purchaseValue;
    private LocalDateTime purchaseDate;
    private List<OrderedItem> orderedItems;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client", nullable = false)
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BigDecimal getPurchaseValue() {
        return purchaseValue;
    }

    public void setPurchaseValue(BigDecimal purchaseValue) {
        this.purchaseValue = purchaseValue;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    public List<OrderedItem> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(List<OrderedItem> orderedItems) {
        this.orderedItems = orderedItems;
    }
}
