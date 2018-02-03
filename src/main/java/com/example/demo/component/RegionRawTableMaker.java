package com.example.demo.component;

import com.example.demo.util.FilePathUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by whilemouse on 18. 1. 11.
 */
@Slf4j
@Component
public class RegionRawTableMaker {

    private static final String[] dbFileExtensions = {"txt"};
    private static final String BUILDING_REGION_RAW_DIRECTORY_NAME = "region_raw";
    private static final int MAX_PARTITION_SIZE = 10000;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public int dropRegionRawTable() {
        final String sql = "DROP TABLE IF EXISTS `region_raw`";
        int result = entityManager
                .createNativeQuery(sql)
                .executeUpdate();
        return result;
    }

    @Transactional
    public int createRegionRawTable() {
        StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE IF NOT EXISTS `region_raw` (  ");
        sql.append("     `id` int(11) NOT NULL AUTO_INCREMENT,  ");
        sql.append("     `rawId` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,  ");
        sql.append("    `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `parent` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `zipCode` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `enabled` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `depth` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `depth1` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `depth2` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `depth3` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `depth4` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `depth5` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `depth1Name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `depth2Name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `depth3Name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `depth4Name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `depth5Name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `fullName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("     PRIMARY KEY (`id`)");
        sql.append("   ) ENGINE=InnoDB DEFAULT CHARSET=utf8  ");

        return entityManager
                .createNativeQuery(sql.toString())
                .executeUpdate();
    }

    @Transactional
    public void initRegionRawTable() throws IOException {
        Path dataFilePath = Paths.get("data");

        dropRegionRawTable();
        createRegionRawTable();

        Consumer<List<String>> instantiationCallback = lines -> {
            StringBuilder mutilInsertQuery = new StringBuilder();
            mutilInsertQuery.append("insert into region_raw(rawId,parent,name,zipCode,description,enabled,depth,depth1,depth2,depth3,depth4,depth5,depth1Name,depth2Name,depth3Name,depth4Name,depth5Name,fullName) values ");
            List<String> insertQueryList = Lists.newArrayList();
            for(String line : lines) {
                String[] lineValues = line.split("\\|");
                StringBuilder insertQuery = new StringBuilder();
                insertQuery.append("('").append(lineValues[0]).append("'")
                        .append(",'").append(lineValues[1]).append("'")
                        .append(",'").append(lineValues[2]).append("'")
                        .append(",'").append(lineValues[3]).append("'")
                        .append(",'").append(lineValues[4]).append("'")
                        .append(",'").append(lineValues[5]).append("'")
                        .append(",'").append(lineValues[6]).append("'")
                        .append(",'").append(lineValues[7]).append("'")
                        .append(",'").append(lineValues[8]).append("'")
                        .append(",'").append(lineValues[9]).append("'")
                        .append(",'").append(lineValues[10]).append("'")
                        .append(",'").append(lineValues[11]).append("'")
                        .append(",'").append(lineValues[12]).append("'")
                        .append(",'").append(lineValues[13]).append("'")
                        .append(",'").append(lineValues[14]).append("'")
                        .append(",'").append(lineValues[15]).append("'")
                        .append(",'").append(lineValues[16]).append("'")
//                        .append(",'").append(lineValues[17]).append("'")
                        .append(",''")
                        .append(")");
                insertQueryList.add(insertQuery.toString());
            }
            mutilInsertQuery.append(String.join(" , ", insertQueryList));

            entityManager.createNativeQuery(mutilInsertQuery.toString()).executeUpdate();
        };

        String rawDirectoryPath = FilePathUtils.getFilePath(dataFilePath, BUILDING_REGION_RAW_DIRECTORY_NAME);
        loadRawList(rawDirectoryPath, instantiationCallback);
    }

    private void loadRawList(String directoryName, Consumer<List<String>> instantiationCallback) throws IOException {
        File dbFolder = new File(directoryName);
        Iterator<File> rawFiles = FileUtils.iterateFiles(dbFolder, dbFileExtensions, true);
        while (rawFiles.hasNext()) {
            File rawDataFile = rawFiles.next();
            buildRawList(rawDataFile, instantiationCallback);
        }
    }

    @Transactional
    private void buildRawList(File rawFile, Consumer<List<String>> instantiationCallback) throws IOException {
        Iterator<String> lines = FileUtils.lineIterator(rawFile);
        List<String> subLines = Lists.newArrayList();

        lines.forEachRemaining(line -> {
            subLines.add(line);
            if(subLines.size() == MAX_PARTITION_SIZE) {
                instantiationCallback.accept(subLines);
                subLines.clear();
            }
        });
        if (!subLines.isEmpty()) {
            instantiationCallback.accept(subLines);
            subLines.clear();
        }
    }

}
