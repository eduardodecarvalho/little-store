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
import com.littlestore.littlestore.domain.dto.ProductDTO;
import com.littlestore.littlestore.service.ProductService;

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/v1/products/{id}")
    public ProductDTO findById(@PathVariable Integer id) {
        return new ProductDTO(productService.findById(id));
    }

    @GetMapping("/v1/products")
    public List<ProductDTO> findProducts() {
        return productService.findAll().stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/v1/products/{id}")
    public void update(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        productDTO.setId(id);
        productService.update(productDTO.getProduct());
    }

    @PostMapping("/v1/products")
    @ResponseStatus(HttpStatus.CREATED)
    public IdDTO create(@RequestBody ProductDTO productDTO) {
        return new IdDTO(productService.create(productDTO.getProduct()));
    }

    @DeleteMapping("/v1/products/{id}")
    public void update(@PathVariable Integer id) {
        productService.delete(id);
    }
}
