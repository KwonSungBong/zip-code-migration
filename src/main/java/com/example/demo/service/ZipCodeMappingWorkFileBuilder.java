package com.example.demo.service;

import com.example.demo.component.RawFileLoader;
import com.example.demo.component.WorkFileMaker;
import com.example.demo.dto.AreaRaw;
import com.example.demo.dto.NewAreaRaw;
import com.example.demo.dto.OldAreaRaw;
import com.example.demo.dto.RegionRaw;
import com.example.demo.entity.Region;
import com.example.demo.entity.ZipCodeMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ksb on 2018. 2. 3..
 */
@Service
public class ZipCodeMappingWorkFileBuilder {

    @Autowired
    private RawFileLoader rawFileLoader;

    @Autowired
    private ZipCodeMappingRawBuilder zipCodeMappingRawBuilder;

    @Autowired
    private WorkFileMaker workFileMaker;

    public void build() throws IOException {
        List<AreaRaw> oldAreaRawList = rawFileLoader.loadOldAreaRawList().stream().distinct().collect(Collectors.toList());
        List<AreaRaw> newAreaRawList = rawFileLoader.loadNewAreaRawList().stream().distinct().collect(Collectors.toList());

        List<ZipCodeMapping> zipCodeMappingList = zipCodeMappingRawBuilder.build(oldAreaRawList, newAreaRawList);

        workFileMaker.saveZipCodeMappingWorkFile(zipCodeMappingList);
    }

}
