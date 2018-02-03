package com.example.demo.service;

import com.example.demo.component.WorkFileLoader;
import com.example.demo.entity.Region;
import com.example.demo.repository.RegionRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * Created by ksb on 2018. 1. 14..
 */
@Service
public class RegionTableUpdater {

    @Autowired
    private WorkFileLoader workFileLoader;

    @Autowired
    private RegionRepository regionRepository;

    public void update() throws IOException {
        List<Region> regionRawList = workFileLoader.loadRegionWorkFile();

        Lists.partition(regionRawList, 1000).forEach(subRegionRawList -> {
            regionRepository.save(subRegionRawList);
        });
    }

}
