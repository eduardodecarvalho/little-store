package com.littlestore.littlestore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.littlestore.littlestore.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByOrderByName();
}
