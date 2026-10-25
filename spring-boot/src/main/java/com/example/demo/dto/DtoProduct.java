package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.entity.EntityProduct;

public record DtoProduct(
        Integer id,
        String sku,
        String name,
        String description,
        String unit,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updateAt) {
    public static DtoProduct from(EntityProduct p) {
        return new DtoProduct(
                p.getId(),
                p.getSku(),
                p.getName(),
                p.getDescription(),
                p.getUnit(),
                p.isActive(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }
}
