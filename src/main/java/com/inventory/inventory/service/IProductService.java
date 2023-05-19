package com.inventory.inventory.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.inventory.inventory.model.Product;

public interface IProductService {
    public ResponseEntity<List<Product>> searchProduct();

    public ResponseEntity<List<Product>> save(Product product, Long categoryId);
}
