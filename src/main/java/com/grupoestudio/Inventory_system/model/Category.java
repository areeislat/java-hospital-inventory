package com.grupoestudio.Inventory_system.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //debe ser unico sin valores nulos
    @Column(nullable=false, unique = true)
    private String name;

    @Column
    private String description;

    //para que cada producto tiene un id unico, este sera la llave foranea
    @OneToMany(mappedBy="category") //union de uno hacia muchos
    private List<Product> products;

    public void setId(Long id){
    
        this.id = id;
    }
    
}
