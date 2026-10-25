package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DtoProduct;
import com.example.demo.dto.DtoProductCreate;
import com.example.demo.service.ServiceProduct;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ApplicationRoutes {

    private final ServiceProduct serviceProduct;

    ApplicationRoutes(ServiceProduct product) {
        log.info("ApplicationRoutes initialized");
        this.serviceProduct = product;
    }

    @GetMapping("/")
    public String root() {
        log.info("/ is called");
        return "Application is running";
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        log.info("/health is called");
        return Map.of("status", "ok");
    }

    @GetMapping("/products")
    public List<DtoProduct> products() {
        return serviceProduct.getProducts();
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public List<DtoProduct> createDtoProduct(@RequestBody List<DtoProductCreate> reqDtoProductCreate) {
        return serviceProduct.createProduct(reqDtoProductCreate);
    }
}
