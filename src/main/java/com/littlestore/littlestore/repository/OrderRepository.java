package com.littlestore.littlestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.littlestore.littlestore.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
