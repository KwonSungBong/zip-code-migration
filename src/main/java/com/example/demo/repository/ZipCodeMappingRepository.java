package com.example.demo.repository;

import com.example.demo.entity.ZipCodeMapping;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by whilemouse on 17. 11. 16.
 */
public interface ZipCodeMappingRepository extends JpaRepository<ZipCodeMapping, String> {
}
