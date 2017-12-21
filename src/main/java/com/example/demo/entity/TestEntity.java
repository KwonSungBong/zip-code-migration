package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.AUTO;

/**
 * Created by whilemouse on 17. 11. 16.
 */
@Entity
@Data
public class TestEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String test;

    public TestEntity clone(boolean isCreate) {
        TestEntity clone = new TestEntity();
        clone.setId(isCreate ? null : this.getId());
        clone.setTest(this.getTest());
        return clone;
    }

}
