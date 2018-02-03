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

    public static final Path DEFAULT_PATH = Paths.get("data");

    public static final String REGION_WORK_FILE_NAME = "region_work_file.txt";
    public static final String ZIP_CODE_MAPPING_WORK_FILE_NAME = "zip_code_mapping_work_file.txt";

    public static final String BASE_WORK_DIRECTORY_NAME = "works";
    public static final String BASE_LOG_DIRECTORY_NAME = "logs";
    public static final String REGION_NEW_AREA_RAW_DIRECTORY_NAME = "new_area";
    public static final String REGION_OLD_AREA_RAW_DIRECTORY_NAME = "old_area";

    public static String getRawFilePath(String fileName) throws IOException {
        return getFilePath(DEFAULT_PATH, fileName);
    }

    public static String getWorkFilePath(String fileName) throws IOException {
        return getFilePath(DEFAULT_PATH, FilePathUtils.createWorkDirectory() + fileName);
    }

    public static String getLogFilePath(String fileName) throws IOException {
        return getFilePath(DEFAULT_PATH, FilePathUtils.createDateLogDirectory() + fileName);
    }

    public static String getFilePath(Path basePath, String fileName) throws NoSuchFileException {
        Path filePath = Paths.get(basePath.toString(), fileName);
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
