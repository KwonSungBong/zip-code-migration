package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by whilemouse on 17. 11. 16.
 */
@Entity
@Data
public class Target {

    @Id
    private Long id;
//    private Region targetRegion;

}