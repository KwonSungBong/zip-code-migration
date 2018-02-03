package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by ksb on 2018. 2. 3..
 */
@Entity
@Data
public class ZipCodeMapping {

    @Id
    private String oldZipCode;
    private String newZipCode;

    public ZipCodeMapping() {}

    public ZipCodeMapping(String line) {
        String[] values = line.split("\\|");

        oldZipCode = values[0];
        newZipCode = values[1];
    }

    public ZipCodeMapping(String oldZipCode, String newZipCode) {
        this.oldZipCode = oldZipCode;
        this.newZipCode = newZipCode;
    }

}
