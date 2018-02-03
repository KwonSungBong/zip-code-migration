package com.example.demo.component;

import com.example.demo.dto.AreaRaw;
import com.example.demo.dto.NewAreaRaw;
import com.example.demo.dto.OldAreaRaw;
import com.example.demo.dto.RangeAreaRaw;
import com.example.demo.util.FilePathUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
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
public class AreaTableMaker {

    private static final String[] dbFileExtensions = {"txt"};
    private static final String FILE_ENCODING_TYPE = "cp949";
    private static final String BUILDING_NEW_AREA_DIRECTORY_NAME = "new_area";
    private static final String BUILDING_OLD_AREA_DIRECTORY_NAME = "old_area";
    private static final String BUILDING_RANGE_AREA_DIRECTORY_NAME = "range_area";
    private static final int MAX_PARTITION_SIZE = 10000;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public int dropNewAreaTable() {
        final String sql = "DROP TABLE IF EXISTS `new_area`";
        int result = entityManager
                .createNativeQuery(sql)
                .executeUpdate();
        return result;
    }

    @Transactional
    public int dropOldAreaTable() {
        final String sql = "DROP TABLE IF EXISTS `old_area`";
        int result = entityManager
                .createNativeQuery(sql)
                .executeUpdate();
        return result;
    }

    @Transactional
    public int dropRangeAreaTable() {
        final String sql = "DROP TABLE IF EXISTS `range_area`";
        int result = entityManager
                .createNativeQuery(sql)
                .executeUpdate();
        return result;
    }

    @Transactional
    public int createNewAreaTable() {
        StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE IF NOT EXISTS `new_area` (  ");
        sql.append("     `id` int(11) NOT NULL AUTO_INCREMENT,  ");
        sql.append("    `area0` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `area1` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `area2` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `area3` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("     `zip_code` char(6) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("     `unique_road_name` varchar(255) DEFAULT NULL, ");
        sql.append("     `building_name` varchar(255) DEFAULT NULL, ");
        sql.append("     PRIMARY KEY (`id`)");
        sql.append("   ) ENGINE=InnoDB DEFAULT CHARSET=utf8  ");

        return entityManager
                .createNativeQuery(sql.toString())
                .executeUpdate();
    }

    @Transactional
    public int createOldAreaTable() {
        StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE IF NOT EXISTS `old_area` (  ");
        sql.append("     `id` int(11) NOT NULL AUTO_INCREMENT,  ");
        sql.append("    `area0` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `area1` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `area2` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `area3` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("     `zip_code` char(6) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("     `unique_road_name` varchar(255) DEFAULT NULL, ");
        sql.append("     `building_name` varchar(255) DEFAULT NULL, ");
        sql.append("     PRIMARY KEY (`id`)");
        sql.append("   ) ENGINE=InnoDB DEFAULT CHARSET=utf8  ");

        return entityManager
                .createNativeQuery(sql.toString())
                .executeUpdate();
    }

    @Transactional
    public int createRangeAreaTable() {
        StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE IF NOT EXISTS `range_area` (  ");
        sql.append("     `id` int(11) NOT NULL AUTO_INCREMENT,  ");
        sql.append("    `area0` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `area1` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `area2` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("    `area3` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("     `zip_code` char(6) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("     PRIMARY KEY (`id`)");
        sql.append("   ) ENGINE=InnoDB DEFAULT CHARSET=utf8  ");

        return entityManager
                .createNativeQuery(sql.toString())
                .executeUpdate();
    }

    @Transactional
    public void initNewAreaTable() throws IOException {
        Path dataFilePath = Paths.get("data");

        dropNewAreaTable();
        createNewAreaTable();

        Consumer<List<String>> instantiationCallback = lines -> {
            StringBuilder mutilInsertQuery = new StringBuilder();
            mutilInsertQuery.append("insert into new_area(area0,area1,area2,area3,zip_code,unique_road_name,building_name) values ");
            List<String> insertQueryList = Lists.newArrayList();
            for(String line : lines) {
                AreaRaw areaRaw = new NewAreaRaw(line);
                StringBuilder insertQuery = new StringBuilder();
                insertQuery.append("('").append(areaRaw.getArea0()).append("'")
                        .append(",'").append(areaRaw.getArea1()).append("'")
                        .append(",'").append(areaRaw.getArea2()).append("'")
                        .append(",'").append(areaRaw.getArea3()).append("'")
                        .append(",'").append(areaRaw.getZipCode()).append("'")
                        .append(",'").append(areaRaw.getUniqueRoadName()).append("'")
                        .append(",'").append(areaRaw.getBuildingName()).append("'")
                        .append(")");
                insertQueryList.add(insertQuery.toString());
            }
            mutilInsertQuery.append(String.join(" , ", insertQueryList));

            entityManager.createNativeQuery(mutilInsertQuery.toString()).executeUpdate();
        };

        String areaRawDirectoryPath = FilePathUtils.getFilePath(dataFilePath, BUILDING_NEW_AREA_DIRECTORY_NAME);
        loadRawList(areaRawDirectoryPath, instantiationCallback);
    }

    @Transactional
    public void initOldAreaTable() throws IOException {
        Path dataFilePath = Paths.get("data");

        dropOldAreaTable();
        createOldAreaTable();

        Consumer<List<String>> instantiationCallback = lines -> {
            StringBuilder mutilInsertQuery = new StringBuilder();
            mutilInsertQuery.append("insert into old_area(area0,area1,area2,area3,zip_code,unique_road_name,building_name) values ");
            List<String> insertQueryList = Lists.newArrayList();
            for(String line : lines) {
                AreaRaw areaRaw = new OldAreaRaw(line);
                StringBuilder insertQuery = new StringBuilder();
                insertQuery.append("('").append(areaRaw.getArea0()).append("'")
                        .append(",'").append(areaRaw.getArea1()).append("'")
                        .append(",'").append(areaRaw.getArea2()).append("'")
                        .append(",'").append(areaRaw.getArea3()).append("'")
                        .append(",'").append(areaRaw.getZipCode()).append("'")
                        .append(",'").append(areaRaw.getUniqueRoadName()).append("'")
                        .append(",'").append(areaRaw.getBuildingName()).append("'")
                        .append(")");
                insertQueryList.add(insertQuery.toString());
            }
            mutilInsertQuery.append(String.join(" , ", insertQueryList));

            entityManager.createNativeQuery(mutilInsertQuery.toString()).executeUpdate();
        };

        String areaRawDirectoryPath = FilePathUtils.getFilePath(dataFilePath, BUILDING_OLD_AREA_DIRECTORY_NAME);
        loadRawList(areaRawDirectoryPath, instantiationCallback);
    }

    @Transactional
    public void initRangeAreaTable() throws IOException {
        Path dataFilePath = Paths.get("data");

        dropRangeAreaTable();
        createRangeAreaTable();

        Consumer<List<String>> instantiationCallback = lines -> {
            StringBuilder mutilInsertQuery = new StringBuilder();
            mutilInsertQuery.append("insert into range_area(area0,area1,area2,area3,zip_code) values ");
            List<String> insertQueryList = Lists.newArrayList();
            for(String line : lines) {
                AreaRaw areaRaw = new RangeAreaRaw(line);
                StringBuilder insertQuery = new StringBuilder();
                insertQuery.append("('").append(areaRaw.getArea0()).append("'")
                        .append(",'").append(areaRaw.getArea1()).append("'")
                        .append(",'").append(areaRaw.getArea2()).append("'")
                        .append(",'").append(areaRaw.getArea3()).append("'")
                        .append(",'").append(areaRaw.getZipCode()).append("'")
                        .append(")");
                insertQueryList.add(insertQuery.toString());
            }
            mutilInsertQuery.append(String.join(" , ", insertQueryList));

            entityManager.createNativeQuery(mutilInsertQuery.toString()).executeUpdate();
        };

        String areaRawDirectoryPath = FilePathUtils.getFilePath(dataFilePath, BUILDING_RANGE_AREA_DIRECTORY_NAME);
        loadRawList(areaRawDirectoryPath, instantiationCallback);
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
        // 우체국에서 다운받은 파일의 인코딩이 cp949이다.
        Iterator<String> lines = FileUtils.lineIterator(rawFile, FILE_ENCODING_TYPE);

        // 첫줄은 title이다. 버리자.
        lines.next();

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
