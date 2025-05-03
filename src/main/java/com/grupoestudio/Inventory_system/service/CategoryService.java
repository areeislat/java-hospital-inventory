package com.grupoestudio.Inventory_system.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupoestudio.Inventory_system.model.Category;
import com.grupoestudio.Inventory_system.model.InventoryMovement;
import com.grupoestudio.Inventory_system.model.Product;
import com.grupoestudio.Inventory_system.repository.CategoryRepository;
import com.grupoestudio.Inventory_system.repository.InventoryMovementRepository;
import com.grupoestudio.Inventory_system.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional //hace que todos los servicios sean transaccionales
public class CategoryService {

    //es un patron de diseño
    //estamos instanciando de manera automatica este repositorio
    //hace que tenga menos codigo
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;

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


    public boolean hasLowStock(Long idCategory, int minStock){
        Category category = categoryRepository.findById(idCategory).get();
        // lista de productos que estamos entregando en ese metodo
        List<Product> products = category.getProducts();

        for (Product product : products) {
            if (product.getStock() < minStock){
                return true;
            }
        }
        return false;
    }

    //metodo que actualice el precio de una categoria bajo cierto porcentaje
    //pasar por parametro el id y el porcentaje a subir (¿bajar tambien?)
    //obtener categoria (id)
    //obtener los productos (como objetos)

    //guardarlos bajo un list los productos

    //hacer un ciclo for que recorra cada precio de cada producto
    //en ese mismo ciclo, realizar el set del nuevo precio (calculo del precio y el porcentaje)

    //errores?
    

    public void setPrice(Long idCategory, double percentage){
        Category category = categoryRepository.findById(idCategory).get();

        List<Product> products = category.getProducts();

        for (Product product : products) {
            double calculo = product.getPrice() * percentage;
            double finalPrice = product.getPrice() + calculo;
            product.setPrice(finalPrice);
        }
    }


    //metodo que desactive todos los productos de un categoria con stock bajo (entregado)
    public int setFalseProduct(Long idCategory, int stock){
        Category category = categoryRepository.findById(idCategory).get();

        List<Product> products = category.getProducts();

        int count = 0;

        for (Product product : products) {
            if(product.getStock() < stock && product.isActive()) {
                product.setActive(false);
                productRepository.save(product);
                count+=1;
            }
        }

        return count;
    }


    //que reasigne todos los productos de un origen a una categoria de destino
    //obtener la categoria
    //obtener 
    public void reassignProduct(Long idCategory, Long idCategoryFinal){
        Category categoryInitial = categoryRepository.findById(idCategory).get();
        Category categoryFinal = categoryRepository.findById(idCategoryFinal).get();

        List<Product> products = categoryInitial.getProducts();

        for (Product product : products) {
            product.setCategory(categoryFinal);
            productRepository.save(product);
            
        }
    }

    //metodo que ajuste el stock de todos los productos de la categoria para que
    //no superen el limite max que pasamos como parametro al metodo
    //por ejemplo si un producto tien mas de 100 stock (parametro que pasamos)
    //lo restamos para que este en ese stock
    //registrar el movimiento con el tipo "AJUSTE"
    public void reassignStockProduct(Long idCategory, int maxStock) {
        Category category= categoryRepository.findById(idCategory).get();

        List<Product> products = category.getProducts();

        for (Product product : products) {
            if(product.getStock() > maxStock) {
                int calculo = product.getStock() - maxStock;
                int calculoFinal = product.getStock() - calculo;
                
                product.setStock(calculoFinal);
                productRepository.save(product);
                
                InventoryMovement modifingMovement = new InventoryMovement();

                modifingMovement.setProduct(product);
                modifingMovement.setQuantity(calculo);
                modifingMovement.setType("AJUSTE");
                modifingMovement.setDate(new Date());

                //se guarda segun método

                inventoryMovementRepository.save(modifingMovement);
            
            }
        }

    }

}
