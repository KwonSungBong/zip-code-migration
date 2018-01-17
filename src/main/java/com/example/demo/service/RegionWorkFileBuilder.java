package com.example.demo.service;

import com.example.demo.dto.RegionRaw;
import com.example.demo.entity.Region;
import com.example.demo.util.FilePathUtils;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ksb on 2018. 1. 14..
 */
@Service
public class RegionWorkFileBuilder {

    private static final String[] dbFileExtensions = {"txt"};
    private static final String FILE_ENCODING_TYPE = "cp949";
    private static final String BUILDING_REGION_RAW_DIRECTORY_NAME = "new_area";

    @Autowired
    private RegionRawBuilder regionRawBuilder;

    public void build() throws IOException {
        Path dataFilePath = Paths.get("data");

        String regionRawDirectoryPath = FilePathUtils.getFilePath(dataFilePath, BUILDING_REGION_RAW_DIRECTORY_NAME);

        List<RegionRaw> regionRawList = loadRawList(regionRawDirectoryPath);

        List<Region> regionList = regionRawBuilder.build(regionRawList);

        saveWorkFile(regionList);
    }

    private List<RegionRaw> loadRawList(String directoryName) throws IOException {
        List<RegionRaw> rawDataList = Lists.newArrayList();
        File dbFolder = new File(directoryName);
        Iterator<File> rawDataFiles = FileUtils.iterateFiles(dbFolder, dbFileExtensions, true);
        while (rawDataFiles.hasNext()) {
            File rawDataFile = rawDataFiles.next();
            List<RegionRaw> parseRawDataList = buildRawDataList(rawDataFile);
            rawDataList.addAll(parseRawDataList);
        }
        return rawDataList;
    }

    private List<RegionRaw> buildRawDataList(File rawDataFile) throws IOException {
        // 우체국에서 다운받은 파일의 인코딩이 cp949이다.
        List<String> lines = FileUtils.readLines(rawDataFile, FILE_ENCODING_TYPE);

        // 첫줄은 title이다. 버리자.
        lines.remove(0);

        List<RegionRaw> rawDataList = Lists.newArrayList();
        for (String line : lines) {
            rawDataList.add(new RegionRaw(line));
        }
        return rawDataList;
    }

    private void saveWorkFile(List<Region> regionList) throws IOException {
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

        String filePath = FilePathUtils.getWorkFilePath(FilePathUtils.ZIP_CODE_MAPPING_WORK_FILE_NAME);
        File file = new File(filePath);
        FileUtils.writeLines(file, lineList);
    }

}
