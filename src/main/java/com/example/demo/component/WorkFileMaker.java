package com.example.demo.component;

import com.example.demo.entity.Region;
import com.example.demo.entity.ZipCodeMapping;
import com.example.demo.util.FilePathUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by ksb on 2018. 2. 3..
 */
@Slf4j
@Component
public class WorkFileMaker {

    private static final String REGION_WORK_FILE_NAME = FilePathUtils.REGION_WORK_FILE_NAME;
    private static final String ZIP_CODE_MAPPING_WORK_FILE_NAME = FilePathUtils.ZIP_CODE_MAPPING_WORK_FILE_NAME;

    public void saveRegionWorkFile(List<Region> regionList) throws IOException {
        List<String> lineList = Lists.newArrayList();

        for(Region region : regionList) {
            StringBuilder sb = new StringBuilder();
            sb.append(region.getId()).append("|");
            sb.append(region.getParent()).append("|");
            sb.append(region.getName()).append("|");
            sb.append(region.getZipCode()).append("|");
            sb.append(region.getDescription()).append("|");
            sb.append(region.isEnabled()).append("|");
            sb.append(region.getDepth()).append("|");
            sb.append(region.getDepth1()).append("|");
            sb.append(region.getDepth2()).append("|");
            sb.append(region.getDepth3()).append("|");
            sb.append(region.getDepth4()).append("|");
            sb.append(region.getDepth5()).append("|");
            sb.append(region.getDepth1Name()).append("|");
            sb.append(region.getDepth2Name()).append("|");
            sb.append(region.getDepth3Name()).append("|");
            sb.append(region.getDepth4Name()).append("|");
            sb.append(region.getDepth5Name()).append("|");
            sb.append(region.getFullName());
            lineList.add(sb.toString());
        }

        String filePath = FilePathUtils.getWorkFilePath(REGION_WORK_FILE_NAME);
        File file = new File(filePath);
        FileUtils.writeLines(file, lineList);
    }

    public void saveZipCodeMappingWorkFile(List<ZipCodeMapping> zipCodeMappingList) throws IOException {
        List<String> lineList = Lists.newArrayList();

        for(ZipCodeMapping zipCodeMapping : zipCodeMappingList) {
            StringBuilder sb = new StringBuilder();
            sb.append(zipCodeMapping.getOldZipCode()).append("|");
            sb.append(zipCodeMapping.getNewZipCode());
            lineList.add(sb.toString());
        }

        String filePath = FilePathUtils.getWorkFilePath(ZIP_CODE_MAPPING_WORK_FILE_NAME);
        File file = new File(filePath);
        FileUtils.writeLines(file, lineList);
    }

}
