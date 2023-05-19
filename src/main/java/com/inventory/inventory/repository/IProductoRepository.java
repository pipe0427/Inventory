package com.inventory.inventory.repository;

import org.springframework.data.repository.CrudRepository;

import com.inventory.inventory.model.Product;

public interface IProductoRepository extends CrudRepository<Product, Long> {

}
