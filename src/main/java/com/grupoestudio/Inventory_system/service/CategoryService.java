package com.grupoestudio.Inventory_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupoestudio.Inventory_system.model.Category;
import com.grupoestudio.Inventory_system.repository.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional //hace que todos los servicios sean transaccionales
public class CategoryService {

    //es un patron de diseño
    //estamos instanciando de manera automatica este repositorio
    //hace que tenga menos codigo
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findall(){
        return categoryRepository.findAll();
    }

    public Category findById(Long id){
        //basicamente dice que si no se encuentra retorne que no se encontró la categoria
        return categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Category not found"));
    }

    //guarda la categoria
    public Category save(Category category){
        return categoryRepository.save(category);
    }

    //es un void porque no necesitamos que retorne algo cuando lo eliminamos
    // INVESTIGAR SOBRE METODOS VOID Y CÓMO Y CUÁNDO SE PUEDEN/DEBEN IMPLEMENTAR
    public void deleteById(Long id){
        categoryRepository.deleteById(id);
    }

}
