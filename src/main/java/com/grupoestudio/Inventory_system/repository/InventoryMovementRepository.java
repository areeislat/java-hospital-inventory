package com.grupoestudio.Inventory_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupoestudio.Inventory_system.model.InventoryMovement;

public interface InventoryMovementRepository  extends JpaRepository<InventoryMovement, Long>{

}
