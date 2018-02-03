package com.example.demo.component;

import com.example.demo.dto.AreaRaw;
import com.example.demo.dto.NewAreaRaw;
import com.example.demo.dto.OldAreaRaw;
import com.example.demo.dto.RegionRaw;
import com.example.demo.util.FilePathUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by ksb on 2018. 2. 3..
 */
@Slf4j
@Component
public class RawFileLoader {

    private static final String[] fileExtensions = {"txt"};
    private static final String FILE_ENCODING_TYPE = "cp949";
    private static final String REGION_NEW_AREA_RAW_DIRECTORY_NAME = FilePathUtils.REGION_NEW_AREA_RAW_DIRECTORY_NAME;
    private static final String REGION_OLD_AREA_RAW_DIRECTORY_NAME = FilePathUtils.REGION_OLD_AREA_RAW_DIRECTORY_NAME;

    public List<RegionRaw> loadRegionRawList() throws IOException {
        String rawDirectoryPath = FilePathUtils.getRawFilePath(REGION_NEW_AREA_RAW_DIRECTORY_NAME);
        Function<String, RegionRaw> instanceCallback = line -> new RegionRaw(line);
        return loadRawList(rawDirectoryPath, instanceCallback);
    }

    public List<AreaRaw> loadNewAreaRawList() throws IOException {
        String rawDirectoryPath = FilePathUtils.getRawFilePath(REGION_NEW_AREA_RAW_DIRECTORY_NAME);
        Function<String, AreaRaw> instanceCallback = line -> new NewAreaRaw(line);
        return loadRawList(rawDirectoryPath, instanceCallback);
    }

    public List<AreaRaw> loadOldAreaRawList() throws IOException {
        String rawDirectoryPath = FilePathUtils.getRawFilePath(REGION_OLD_AREA_RAW_DIRECTORY_NAME);
        Function<String, AreaRaw> instanceCallback = line -> new OldAreaRaw(line);
        return loadRawList(rawDirectoryPath, instanceCallback);
    }

    private <T> List<T> loadRawList(String rawDirectoryPath, Function<String, T> instanceCallback) throws IOException {
        List<T> rawList = Lists.newArrayList();
        File rawDirectory = new File(rawDirectoryPath);
        Iterator<File> rawFiles = FileUtils.iterateFiles(rawDirectory, fileExtensions, true);
        while (rawFiles.hasNext()) {
            File rawFile = rawFiles.next();
            List<T> parseRawList = loadRawList(rawFile, instanceCallback);
            rawList.addAll(parseRawList);
        }
        return rawList;
    }

    private <T> List<T> loadRawList(File rawFile, Function<String, T> instanceCallback) throws IOException {
        // 우체국에서 다운받은 파일의 인코딩이 cp949이다.
        List<String> lines = FileUtils.readLines(rawFile, FILE_ENCODING_TYPE);

        // 첫줄은 title이다. 버리자.
        lines.remove(0);

        List<T> rawList = Lists.newArrayList();
        for (String line : lines) {
            rawList.add(instanceCallback.apply(line));
        }
        return rawList.stream().distinct().collect(Collectors.toList());
    }

}
