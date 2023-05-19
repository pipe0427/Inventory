package com.inventory.inventory.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.inventory.inventory.model.Product;
import com.inventory.inventory.service.IProductService;
import com.inventory.inventory.util.Util;

import lombok.AllArgsConstructor;

@RestController
// @AllArgsConstructor
@RequestMapping("/api/v1/")
public class ProductRestController {
    
    public IProductService productService;

    public ProductRestController(IProductService productService){
        this.productService = productService;
    }


    @GetMapping("products")
    public ResponseEntity<List<Product>> searchProducts(){
        return productService.searchProduct();
    }
    
    @PostMapping("products")
    public ResponseEntity<List<Product>> saveProduct(@RequestParam("picture") MultipartFile picture,@RequestParam("name") String name,
    @RequestParam("price") double price,@RequestParam("quantity") int quantity,@RequestParam("categoryId") Long categoryId)throws IOException
    {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setPicture(Util.compressZLib(picture.getBytes()));
        return productService.save(product,categoryId);
    }

}
