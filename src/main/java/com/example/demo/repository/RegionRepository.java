package com.example.demo.repository;

import com.example.demo.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by whilemouse on 17. 11. 16.
 */
public interface RegionRepository extends JpaRepository<Region, Long> {
}
