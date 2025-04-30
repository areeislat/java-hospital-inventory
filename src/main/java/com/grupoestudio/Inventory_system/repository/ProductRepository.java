package com.grupoestudio.Inventory_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.grupoestudio.Inventory_system.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
