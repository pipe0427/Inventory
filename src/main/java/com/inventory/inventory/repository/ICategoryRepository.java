package com.inventory.inventory.repository;

import org.springframework.data.repository.CrudRepository;

import com.inventory.inventory.model.Category;

public interface ICategoryRepository extends CrudRepository<Category,Long>{
    
}
