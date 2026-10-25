package com.example.demo.repository;

import com.example.demo.entity.EntityStockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementRepository extends JpaRepository<EntityStockMovement, Integer> {
}
