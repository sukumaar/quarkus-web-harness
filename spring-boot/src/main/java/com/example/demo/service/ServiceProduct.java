package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.DtoProduct;
import com.example.demo.dto.DtoProductCreate;
import com.example.demo.entity.EntityProduct;
import com.example.demo.repository.ProductRepository;

@Service
public class ServiceProduct {
    private final ProductRepository productRepository;

    public ServiceProduct(ProductRepository p) {
        this.productRepository = p;
    }

    public List<DtoProduct> getProducts() {
        List<EntityProduct> products = productRepository.findAll();
        List<DtoProduct> result = new ArrayList<>(products.size());

        for (EntityProduct product : products) {
            result.add(DtoProduct.from(product));
        }

        return result;
    }

    public List<DtoProduct> createProduct(List<DtoProductCreate> p) {
        List<EntityProduct> entities = new ArrayList<>(p.size());
        for (DtoProductCreate request : p) {
            EntityProduct product = new EntityProduct();
            product.setSku(request.sku());
            product.setName(request.name());
            product.setDescription(request.description());
            entities.add(product);
        }

        productRepository.saveAll(entities);
        List<DtoProduct> result = new ArrayList<>(entities.size());
        for (EntityProduct product : entities) {
            result.add(DtoProduct.from(product));
        }

        return result;
    }
}
