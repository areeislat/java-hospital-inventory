package com.grupoestudio.Inventory_system.service;

import java.util.Date;
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

    public void transferStock(Long idSourceProduct, Long idTargetProduct, int quantity){
        Product sourceProduct = productRepository.findById(idTargetProduct).get();
        Product targetProduct = productRepository.findById(idTargetProduct).get();

        //hace un if para realizar la validacion, que traiga un mensaje de error cuando no 
        //hay stock suficiente
        if(sourceProduct.getStock() <- quantity){
            throw new RuntimeException("Not enough stock in the source product.");
        }

        //crear movimiento de salida
        InventoryMovement exitMovement = new InventoryMovement();
        exitMovement.setProduct(sourceProduct);
        exitMovement.setQuantity(quantity);
        exitMovement.setType("SALIDA");
        exitMovement.setDate(new Date());
        sourceProduct.setStock(sourceProduct.getStock() - quantity);
        //se guarda segun método
        productRepository.save(sourceProduct);
        // crear un momento de entrada

        InventoryMovement entryMovement = new InventoryMovement();
        //verificar logica de si es target o source
        entryMovement.setProduct(targetProduct);
        entryMovement.setQuantity(quantity);
        entryMovement.setType("ENTRADA");
        entryMovement.setDate(new Date());
        targetProduct.setStock(targetProduct.getStock() + quantity);
        //se guarda segun método
        productRepository.save(targetProduct);


        inventoryMovementRepository.save(entryMovement);
        inventoryMovementRepository.save(exitMovement);
        

    }

}
