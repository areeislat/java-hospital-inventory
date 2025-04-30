package com.grupoestudio.Inventory_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupoestudio.Inventory_system.model.InventoryMovement;
import com.grupoestudio.Inventory_system.model.Product;
import com.grupoestudio.Inventory_system.repository.InventoryMovementRepository;
import com.grupoestudio.Inventory_system.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InventoryMovementService {

    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<InventoryMovement> findall(){
        return inventoryMovementRepository.findAll();
    }

    public InventoryMovement findById(Long id){
        return inventoryMovementRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public void deleteById(Long id){
        inventoryMovementRepository.deleteById(id);
    }

    public InventoryMovement save(InventoryMovement inventoryMovement){
        //Product product = productRepository.findById(inventoryMovement.getProduct().getId());
        Long idProducto = inventoryMovement.getProduct().getId();
        Product product1 = productRepository.findById(idProducto).orElseThrow(() -> new RuntimeException("Product not found"));
        //hago mi variable product
        int currentStock = product1.getStock();
        if (inventoryMovement.getType().equals("ENTRADA")){
            product1.setStock(currentStock + inventoryMovement.getQuantity());
        } else if (inventoryMovement.getType().equals("SALIDA")){
            if(currentStock < inventoryMovement.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product1.getName());
            } 
            product1.setStock(currentStock - inventoryMovement.getQuantity());
        }
        productRepository.save(product1);

        return inventoryMovementRepository.save(inventoryMovement);
    }

}
