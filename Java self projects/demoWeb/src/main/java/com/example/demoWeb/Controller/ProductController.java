package com.example.demoWeb.Controller;

import com.example.demoWeb.modle.Product;
import com.example.demoWeb.service.ProductService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class ProductController {

    @Autowired
    ProductService service;

    @RequestMapping("/products")
    public List<Product> getProducts(){
        return service.getProducts();
    }

    @RequestMapping("/products/{prodID}")
    public Product getProduct(@PathVariable int prodID){
        return service.getProductsByID(prodID);
    }


    @PostMapping("/products")
    public void addProduct(@RequestBody Product prod){
        service.addProduct(prod);
    }

    @PutMapping("/products")
    public void updateProduct(@RequestBody Product prod){
        service.updateProduct(prod);
    }

    @DeleteMapping("/products/{prodId}")
    public void deleteProduct(@PathVariable int prodId){
        service.deleteProduct(prodId);
    }

//    @RequestMapping("/products/{prodName}")
//    public Product getProduct(@PathParam("prodName") String prodName){
//        return service.getProductsByName(prodName);
//    }
}
