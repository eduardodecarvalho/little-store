package com.littlestore.littlestore.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.littlestore.littlestore.domain.Product;
import com.littlestore.littlestore.domain.dto.ProductDTO;
import com.littlestore.littlestore.repository.ProductRepository;
import com.littlestore.littlestore.utils.BusinessException;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findById(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new BusinessException(BusinessException.PRODUCT_NOT_FOUND));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional
    public Integer create(ProductDTO productDTO) {
        return productRepository.save(productDTO.getProduct()).getId();
    }

    public void delete(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new BusinessException(BusinessException.CLIENT_NOT_FOUND));
        if (product.getQuantity() > 0) {
            throw new BusinessException(BusinessException.CANNOT_DELETE_PRODUCT_IN_STOCK);
        }
        productRepository.delete(product);
    }
}
