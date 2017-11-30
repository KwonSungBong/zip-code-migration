package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Region {

    @Id
    private Long id;
//    private Region parent;

}
