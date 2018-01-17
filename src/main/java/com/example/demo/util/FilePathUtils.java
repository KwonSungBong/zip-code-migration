package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class FilePathUtils {

    public static final String REGION_WORK_FILE_NAME = "region_temp.txt";
    public static final String ZIP_CODE_MAPPING_WORK_FILE_NAME = "zip_code_mapping_temp.txt";

    public static String BASE_WORK_DIRECTORY_NAME = "works";
    public static String BASE_LOG_DIRECTORY_NAME = "logs";

    public static String getWorkFilePath(String fileName) throws IOException {
        return FilePathUtils.createWorkDirectory() + fileName;
    }

    public static String getLogFilePath(String fileName) throws IOException {
        return FilePathUtils.createDateLogDirectory() + fileName;
    }

    public static String getFilePath(Path basePath, String fileName) throws NoSuchFileException {
        Path filePath = Paths.get(basePath.toString(), fileName);

        if(!Files.exists(filePath)){
            throw new NoSuchFileException("file is not found. filePath="+filePath);
        }

        log.debug("filePath={}, basePath={}", filePath, basePath);

        return filePath.toString();
    }

    public static void saveLogFile(List<String> lineList, String fileName) throws IOException {
        String filePath = getLogFilePath(fileName);
        File file = new File(filePath);
        FileUtils.writeLines(file, lineList);
    }

    private static String createWorkDirectory() throws IOException {
        Path WORK_DIRECTORY_PATH = Paths.get(BASE_WORK_DIRECTORY_NAME);

        if(Files.notExists(WORK_DIRECTORY_PATH)){
            Files.createDirectories(WORK_DIRECTORY_PATH);
        }

        return WORK_DIRECTORY_PATH + File.separator;
    }

    private static String createDateLogDirectory() throws IOException {
        Path LOG_DIRECTORY_PATH = Paths.get(BASE_LOG_DIRECTORY_NAME, DateTime.now().toString("yyyy-MM-dd"));

        if(Files.notExists(LOG_DIRECTORY_PATH)){
            Files.createDirectories(LOG_DIRECTORY_PATH);
        }

        return LOG_DIRECTORY_PATH + File.separator;
    }

}
