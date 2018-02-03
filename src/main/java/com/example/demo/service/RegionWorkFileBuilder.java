package com.example.demo.service;

import com.example.demo.component.RawFileLoader;
import com.example.demo.component.WorkFileMaker;
import com.example.demo.dto.RegionRaw;
import com.example.demo.entity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by ksb on 2018. 1. 14..
 */
@Service
public class RegionWorkFileBuilder {

    @Autowired
    private RawFileLoader rawFileLoader;

    @Autowired
    private RegionRawBuilder regionRawBuilder;

    @Autowired
    private WorkFileMaker workFileMaker;

    public void build() throws IOException {
        List<RegionRaw> regionRawList = rawFileLoader.loadRegionRawList();

        List<Region> regionList = regionRawBuilder.build(regionRawList);

        workFileMaker.saveRegionWorkFile(regionList);
    }

}
