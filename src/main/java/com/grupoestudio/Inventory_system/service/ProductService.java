package com.grupoestudio.Inventory_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupoestudio.Inventory_system.model.Product;
import com.grupoestudio.Inventory_system.repository.CategoryRepository;
import com.grupoestudio.Inventory_system.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> findall(){
        return productRepository.findAll();
    }

    public Product findById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    public void deleteById(Long id){
        productRepository.deleteById(id);
    }

    public double calculatePriceTotal(Long id) {
        Product product = productRepository.findById(id).get();
        double productPrice = product.getPrice();
        int stockNumber = product.getStock();
        double totalPrice = productPrice * (double) stockNumber;
        return totalPrice;
    }


}
