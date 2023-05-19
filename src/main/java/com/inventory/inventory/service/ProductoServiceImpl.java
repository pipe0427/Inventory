package com.inventory.inventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.inventory.model.Category;
import com.inventory.inventory.model.Product;
import com.inventory.inventory.repository.ICategoryRepository;
import com.inventory.inventory.repository.IProductoRepository;
import com.inventory.inventory.util.Util;

@Service
public class ProductoServiceImpl implements IProductService {

    private IProductoRepository productoRepository;

    private ICategoryRepository categoryRepository;

    private List<Product> products;

    public ProductoServiceImpl(IProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<Product>> searchProduct() {
        try {
            if(products.size()>0){
                products.forEach(p -> {
                    byte[] imageDescompressed = Util.decompressZLib(p.getPicture());
                    p.setPicture(imageDescompressed);
                    this.products.add(p);
                } );
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(products,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Product>> save(Product product, Long categoryId) {
        try {
            Optional<Category> category = categoryRepository.findById(categoryId);
            if(category.isPresent()){
                product.setCategory(category.get());

            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Product productSaved = productoRepository.save(product);

            if(productSaved != null){
                products.add(productSaved);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();;
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
