package com.example.demo.component;

import com.example.demo.query.RegionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by ksb on 2018. 1. 15..
 */
@Component
public class IdGenerator {

    private int lastId;

    @Autowired
    private RegionQuery regionQuery;

    @PostConstruct
    private void init(){
        Integer lastId = regionQuery.loadLastId();
        if(lastId == null) {
            this.lastId = 0;
        } else {
            this.lastId = lastId;
        }
    }

    public int getId() {
        return lastId;
    }

    public int getNewId() {
        return ++lastId;
    }

}
