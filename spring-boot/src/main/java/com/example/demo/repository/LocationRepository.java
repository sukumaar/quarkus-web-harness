package com.example.demo.repository;

import com.example.demo.entity.EntityLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<EntityLocation, Integer> {
}
