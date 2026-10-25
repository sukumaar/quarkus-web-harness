package com.example.demo.repository;

import com.example.demo.entity.EntityInventoryBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryBalanceRepository extends JpaRepository<EntityInventoryBalance, Integer> {
}
