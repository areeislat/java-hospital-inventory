package com.grupoestudio.Inventory_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupoestudio.Inventory_system.model.Category;
import com.grupoestudio.Inventory_system.service.CategoryService;

@RestController
//contiene la url inicial
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // todos los controlodores debe tener una respuesta tipo responseEntity
    // el responseEntity son las respuesta con http como lo son 400, 404
    // el listado de respuesta que debemos entregar con nuestro servicio
    // el responseentity se rellena con lo que debemso responder
    // (son los codigos) cabecera : respuesta 404 402
    // (con lo que rellenaron la respuesta) body : list<Category> es decir, las categorias
    @GetMapping
    public ResponseEntity<List<Category>> listAllCategories(){
        //variable local categories = list<category>
        List<Category> categories = categoryService.findall();
        //lo de arriba puede estar vacio o puede no estar vacio
        //se hace la pregunta, si es que esta vacio, lo que hace es devolver las categorias
        return categories.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<Category> saveCategory(@RequestBody Category newcategory){
        Category category1 = categoryService.save(newcategory);
        //devuelve el titulo con el status de creado con el cuerpo de la categoria creada
        return ResponseEntity.status(HttpStatus.CREATED).body(newcategory);
    }

    //  el cliente esta ingresando un id en la url y nosotros tenemos que responde con la
    // informacion del id que tenemos guardado
    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        try {
            Category category = categoryService.findById(id);
            // responde con la categoria
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            // retorna que no se encontró y no tiene cuerpo
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            //la categoria que ya existe, la llamamos por el id que estamos llamados
            Category existingCategory = categoryService.findById(id);
            // le actualizamos el id
            category.setId(id);
            Category updatedCategory = categoryService.save(category);
            return ResponseEntity.ok(updatedCategory);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        try {
            categoryService.deleteById(id);
                return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
}
