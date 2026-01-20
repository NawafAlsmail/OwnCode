package com.example.demoWeb.Controller;

import com.example.demoWeb.config.GenericSwaggerDocs;
import com.example.demoWeb.config.SwaggerVisibility;
import com.example.demoWeb.modle.Product;
import com.example.demoWeb.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api")
@SwaggerVisibility
@Tag(name = "Product API", description = "APIs for Product")
public class ProductController {

    @Autowired
    ProductService service;

    @Operation(summary = "get all products")
    @GetMapping("/products")
    public List<Product> getProducts(){
        return service.getProducts();
    }

    @Operation(summary = "get product based on ID")
    @GetMapping("/products/{prodID}")
    public Product getProduct(@PathVariable int prodID){
        return service.getProductsByID(prodID);
    }

    @GenericSwaggerDocs.AddProduct
    @PostMapping("/products")
    public Product addProduct(@RequestBody Product prod){
        return service.addProduct(prod);
    }

    @Operation(summary = "update a product")
    @PutMapping("/products")
    public void updateProduct(@RequestBody Product prod){
        service.updateProduct(prod);
    }

    @GenericSwaggerDocs.DeleteProduct
    @DeleteMapping("/products/{prodId}")
    public void deleteProduct(@PathVariable int prodId){
        service.deleteProduct(prodId);
    }

//    @RequestMapping("/products/{prodName}")
//    public Product getProduct(@PathParam("prodName") String prodName){
//        return service.getProductsByName(prodName);
//    }
}
