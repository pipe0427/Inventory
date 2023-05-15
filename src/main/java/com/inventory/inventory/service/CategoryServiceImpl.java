package com.inventory.inventory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.inventory.model.Category;
import com.inventory.inventory.repository.ICategoryRepository;

@Service
public class CategoryServiceImpl implements ICategoryService {
    
    private final ICategoryRepository categoryRepository;

    List<Category> categories = new ArrayList<>();

    public CategoryServiceImpl (ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<Category>> search() {
        try{
            categories = (List<Category>) categoryRepository.findAll();
            return new ResponseEntity<>(categories,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(categories,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Category>> save(Category category) {
        try{
            Category categorySaved = categoryRepository.save(category);
            categories.add(categorySaved);
            return new ResponseEntity<>(categories,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(categories,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Category> searchById(Long id) {
        try{
            Optional<Category> category = categoryRepository.findById(id);
            if(category.isPresent()){
                return new ResponseEntity<>(category.get(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Category>> update(Category category, Long id) {
        try {
            Optional<Category> categorySearch = categoryRepository.findById(id);
            if(categorySearch.isPresent()){
                categorySearch.get().setName(category.getName());
                categorySearch.get().setDescription(category.getDescription());
                Category categoryToUpdate = categoryRepository.save(categorySearch.get());
                categories.add(categoryToUpdate);
                return new ResponseEntity<>(categories,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Category>> delete(Long id) {
        try {
            categoryRepository.deleteById(id);
            return new ResponseEntity<>(categories,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
}
