package com.grupoestudio.Inventory_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupoestudio.Inventory_system.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
