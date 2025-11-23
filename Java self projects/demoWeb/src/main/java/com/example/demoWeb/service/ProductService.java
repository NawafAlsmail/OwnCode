package com.example.demoWeb.service;

import com.example.demoWeb.Repository.ProductRepo;
import com.example.demoWeb.modle.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo repo;
//
//    List<Product> products = new ArrayList<>( Arrays.asList(
//            new Product(101, "Iphone", 5000),
//            new Product(102, "Galaxy", 2000),
//            new Product(103, "Hawaii", 4000)));

    public List<Product> getProducts(){
        return repo.findAll();
    }

    public Product getProductsByID(int prodId) {

        return repo.findById(prodId).orElse(null);

//        return products.stream()
//                .filter(p -> p.getProdId() == prodId)
//                .findFirst().get();
    }

    public Product addProduct(Product prod) {
//        products.add(prod);
        return repo.save(prod);
    }

    public void updateProduct(Product prod){

        repo.save(prod);
//        int index = 0;
//        for(int i=0; i<products.size(); i++)
//            if(products.get(i).getProdId() == prod.getProdId())
//                index=i;
//        products.set(index,prod);
    }

    public void deleteProduct(int prodId) {

        repo.deleteById(prodId);
//        int index = 0;
//        for(int i=0; i<products.size(); i++)
//            if(products.get(i).getProdId() == prodId)
//                index=i;
//
//        products.remove(index);
    }


//    public Product getProductsByName(String prodName) {
//
//        return products.stream()
//                .filter(p -> p.getProdName() == prodName)
//                .findFirst().get();
//    }
}
