package com.example.demo.service;

import com.example.demo.component.WorkFileLoader;
import com.example.demo.entity.Region;
import com.example.demo.entity.ZipCodeMapping;
import com.example.demo.repository.RegionRepository;
import com.example.demo.repository.ZipCodeMappingRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by ksb on 2018. 2. 3..
 */
@Service
public class ZipCodeMappingTableUpdater {

    @Autowired
    private WorkFileLoader workFileLoader;

    @Autowired
    private ZipCodeMappingRepository zipCodeMappingRepository;

    public void update() throws IOException {
        List<ZipCodeMapping> zipCodeMappingList = workFileLoader.loadZipCodeMappingWorkFile();

        Lists.partition(zipCodeMappingList, 1000).forEach(subZipCodeMappingList -> {
            zipCodeMappingRepository.save(subZipCodeMappingList);
        });
    }

}
