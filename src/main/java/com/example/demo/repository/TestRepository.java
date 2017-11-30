package com.example.demo.repository;

import com.example.demo.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by whilemouse on 17. 11. 16.
 */
public interface TestRepository extends JpaRepository<TestEntity, Long>, JpaSpecificationExecutor {
}
