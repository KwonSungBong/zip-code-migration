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
import java.util.function.Function;

/**
 * Created by ksb on 2018. 2. 3..
 */
@Slf4j
@Component
public class WorkFileLoader {

    public static final String REGION_WORK_FILE_NAME = FilePathUtils.REGION_WORK_FILE_NAME;
    public static final String ZIP_CODE_MAPPING_WORK_FILE_NAME = FilePathUtils.ZIP_CODE_MAPPING_WORK_FILE_NAME;

    public List<Region> loadRegionWorkFile() throws IOException {
        String workFilePath = FilePathUtils.getWorkFilePath(REGION_WORK_FILE_NAME);
        Function<String, Region> instanceCallback = line -> new Region(line);
        return loadWorkFile(workFilePath, instanceCallback);
    }

    public List<ZipCodeMapping> loadZipCodeMappingWorkFile() throws IOException {
        String workFilePath = FilePathUtils.getWorkFilePath(ZIP_CODE_MAPPING_WORK_FILE_NAME);
        Function<String, ZipCodeMapping> instanceCallback = line -> new ZipCodeMapping(line);
        return loadWorkFile(workFilePath, instanceCallback);
    }

    private <T> List<T> loadWorkFile(String workFilePath, Function<String, T> instanceCallback) throws IOException {
        File workFile = new File(workFilePath);
        List<T> rawList = loadWorkFile(workFile, instanceCallback);
        return rawList;
    }

    private <T> List<T> loadWorkFile(File workFile, Function<String, T> instanceCallback) throws IOException {
        List<String> lines = FileUtils.readLines(workFile);
        List<T> dataList = Lists.newArrayList();
        for (String line : lines) {
            dataList.add(instanceCallback.apply(line));
        }
        return dataList;
    }

}
