package com.example.demoWeb.Controller;

import com.example.demoWeb.config.SwaggerVisibility;
import com.example.demoWeb.modle.Product;
import com.example.demoWeb.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
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

    @Operation(
            summary = "Add Product",
            description = "Adding product to the schema",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = Product.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK. Your request has been received", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class ))),
                    @ApiResponse(responseCode = "400", description = "Denied. Invalid details provided", content = @Content(schema = @Schema(example = ""))),
                    @ApiResponse(responseCode = "500", description = "Something went wrong. Error while calling client call", content = @Content(schema = @Schema(example = "")))
            }
    )
    @PostMapping("/products")
    public Product addProduct(@RequestBody Product prod){
        return service.addProduct(prod);
    }
    @Operation(summary = "update a product")
    @PutMapping("/products")
    public void updateProduct(@RequestBody Product prod){
        service.updateProduct(prod);
    }
    @Operation(
            summary = "Deleting product",
            description = "Deleting product from the schema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK. Your request has been received", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class ))),
                    @ApiResponse(responseCode = "400", description = "Denied. Invalid details provided", content = @Content(schema = @Schema(example = ""))),
                    @ApiResponse(responseCode = "500", description = "Something went wrong. Error while calling client call", content = @Content(schema = @Schema(example = "")))
            }
    )
    @DeleteMapping("/products/{prodId}")
    public void deleteProduct(@PathVariable int prodId){
        service.deleteProduct(prodId);
    }

//    @RequestMapping("/products/{prodName}")
//    public Product getProduct(@PathParam("prodName") String prodName){
//        return service.getProductsByName(prodName);
//    }
}
