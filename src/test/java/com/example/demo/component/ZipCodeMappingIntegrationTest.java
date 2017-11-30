package com.example.demo.component;

import com.example.demo.dto.NewAreaData;
import com.example.demo.dto.OldAreaData;
import com.example.demo.query.AreaMappingQuery;
import com.example.demo.query.NewAreaQuery;
import com.example.demo.query.OldAreaQuery;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("local")
//@Rollback(false)
public class ZipCodeMappingIntegrationTest {

    @Autowired
    NewAreaQuery newAreaQuery;

    @Autowired
    OldAreaQuery oldAreaQuery;

    @Autowired
    AreaMappingQuery areaMappingQuery;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void 집코드맵핑안된데이터가져오기() {
        Map<String, List<Object[]>> newResult = Maps.newHashMap();
        Map<String, List<Object[]>> oldResult = Maps.newHashMap();

        List<Object[]> areaList = areaMappingQuery.getArea01();
        for(Object[] area : areaList) {
            String area0 = (String)area[0];
            String area1 = (String)area[1];

            List<Object[]> regionNewTempList = areaMappingQuery.getNotInNewArea(area0, area1);
            List<Object[]> regionOldTempList = areaMappingQuery.getNotInOldArea(area0, area1);

            newResult.put(area0+"/"+area1, regionNewTempList);
            oldResult.put(area0+"/"+area1, regionOldTempList);
        }

        log.debug("- - - - - test complete - - - - -");
    }

    @Test
    public void 나누어서집코드매핑하기() {
        areaMappingQuery.drop();
        areaMappingQuery.create();

        List<Object[]> areaList = areaMappingQuery.getArea01();

        for(Object[] area : areaList) {
            String area0 = (String)area[0];
            String area1 = (String)area[1];
            areaMappingQuery.insert(area0, area1);
        }

        log.debug("- - - - - test complete - - - - -");
    }

    @Test
    public void 한방에집코드매핑하기() {
        areaMappingQuery.drop();
        areaMappingQuery.create();
        areaMappingQuery.insert();

        log.debug("- - - - - test complete - - - - -");
    }

    @Test
    public void 신우편번호확인() throws IOException {
        List<NewAreaData> dbDataList = getNewAreaDataList();

        //시도, 시군구, 읍면, 도로명 항목으로 그룹핑하여 2개이상인 항목을 확인
        Map<String, List<NewAreaData>> test = Maps.filterValues(dbDataList.stream().distinct().collect(Collectors.groupingBy(NewAreaData::condition)), list -> list.size() > 1);

        Map<String, List<String>> test2 = dbDataList.stream()
                .distinct()
                .collect(Collectors.groupingBy(NewAreaData::getUniqueRoadName))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().stream().map(v -> v.getArea0()+"/"+v.getArea1()).distinct().collect(Collectors.toList())));

        //도로명으로 그룹핑하여 시군구가 2개이상인 항목을 확인
        Map<String, List<String>> test3 = Maps.filterValues(test2, list -> list.size() > 1);

        System.out.println();

    }

    @Test
    public void 신우편주소DB추가() throws IOException {
        String logDirectoryPath = createDateLogDirectory();

        newAreaQuery.drop();
        newAreaQuery.create();
        newAreaQuery.insertWriteFile(logDirectoryPath + "new_area_data.txt");
    }

    @Test
    public void 신우편주소생성() throws IOException {
        List<NewAreaData> dbDataList = getNewAreaDataList().stream().distinct().collect(Collectors.toList());
        String logDirectoryPath = createDateLogDirectory();
        saveAsFile1(dbDataList, logDirectoryPath + "new_area_data.txt");

        log.debug("- - - - - test complete - - - - -");
    }

    @Test
    public void 구우편주소확인() throws IOException {
        List<OldAreaData> dbOldDataList = getDbOldDataList();

        //시도, 시군구, 읍면, 도로명 항목으로 그룹핑하여 2개이상인 항목을 확인
        Map<String, List<OldAreaData>> test = Maps.filterValues(dbOldDataList.stream().distinct().collect(Collectors.groupingBy(OldAreaData::condition)), list -> list.size() > 1);

        Map<String, List<String>> test2 = dbOldDataList.stream()
                .distinct()
                .collect(Collectors.groupingBy(OldAreaData::getUniqueRoadName))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().stream().map(v -> v.getArea0()+"/"+v.getArea1()).distinct().collect(Collectors.toList())));

        //도로명으로 그룹핑하여 시군구가 2개이상인 항목을 확인
        Map<String, List<String>> test3 = Maps.filterValues(test2, list -> list.size() > 1);

        System.out.println();

    }

    @Test
    public void 구우편주소DB추가() throws IOException {
        String logDirectoryPath = createDateLogDirectory();

        oldAreaQuery.drop();
        oldAreaQuery.create();
        oldAreaQuery.insertWriteFile(logDirectoryPath + "old_area_data.txt");
    }

    @Test
    public void 구우편주소생성() throws IOException {
        List<OldAreaData> dbOldDataList = getDbOldDataList().stream().distinct().collect(Collectors.toList());
        String logDirectoryPath = createDateLogDirectory();
        saveAsFile2(dbOldDataList, logDirectoryPath + "old_area_data.txt");

        log.debug("- - - - - test complete - - - - -");
    }

    private List<NewAreaData> getNewAreaDataList() throws IOException {
        final String[] dbFileExtensions = {"txt"};

        List<NewAreaData> dbDataList = Lists.newArrayList();
        File dbFolder = new File("data/new_area");
        Iterator<File> dbFiles = FileUtils.iterateFiles(dbFolder, dbFileExtensions, true);
        while (dbFiles.hasNext()) {
            File dbFile = dbFiles.next();
            List<NewAreaData> aDbDataList = buildDbDataList(dbFile);
            dbDataList.addAll(aDbDataList);
//            break;
        }
        return dbDataList;
    }

    private List<OldAreaData> getDbOldDataList() throws IOException {
        final String[] dbFileExtensions = {"txt"};

        List<OldAreaData> dbOldDataList = Lists.newArrayList();
        File dbFolder = new File("data/old_area");
        Iterator<File> dbFiles = FileUtils.iterateFiles(dbFolder, dbFileExtensions, true);
        while (dbFiles.hasNext()) {
            File dbFile = dbFiles.next();
            List<OldAreaData> aDbOldDataList = buildDbOldDataList(dbFile);
            dbOldDataList.addAll(aDbOldDataList);
//            break;
        }
        return dbOldDataList;
    }

    private static List<NewAreaData> buildDbDataList(File dbFile) throws IOException {
        String FILE_ENCODING_TYPE = "cp949";

        // 우체국에서 다운받은 파일의 인코딩이 cp949이다.
        List<String> lines = FileUtils.readLines(dbFile, FILE_ENCODING_TYPE);
        log.info("loading {} finished. {} lines.", dbFile, lines.size());

        // 첫줄은 title이다. 버리자.
        lines.remove(0);

        return buildDbDataList(lines);

    }

    private static List<NewAreaData> buildDbDataList(List<String> lines) {
        List<NewAreaData> dbDataList = Lists.newArrayList();
        for (String line : lines) {
            dbDataList.add(new NewAreaData(line));
        }
        return dbDataList;
    }

    private void saveAsFile1(List<NewAreaData> dbDataList, String fileName) throws IOException {
        List<String> lineList = Lists.newArrayList();
        for(NewAreaData newData : dbDataList) {
            StringBuilder sb = new StringBuilder();
            sb.append("0").append("|");
            sb.append(newData.getArea0()).append("|");
            sb.append(newData.getArea1()).append("|");
            sb.append(newData.getArea2()).append("|");
            sb.append(newData.getArea3()).append("|");
            sb.append(newData.getZipCode()).append("|");
            sb.append(newData.getUniqueRoadName());
            lineList.add(sb.toString());
        }

        File file = new File(fileName);
        FileUtils.writeLines(file, lineList);
    }

    private void saveAsFile2(List<OldAreaData> dbOldDataList, String fileName) throws IOException {
        List<String> lineList = Lists.newArrayList();
        for(OldAreaData oldData : dbOldDataList) {
            StringBuilder sb = new StringBuilder();
            sb.append("0").append("|");
            sb.append(oldData.getArea0()).append("|");
            sb.append(oldData.getArea1()).append("|");
            sb.append(oldData.getArea2()).append("|");
            sb.append(oldData.getArea3()).append("|");
            sb.append(oldData.getZipCode()).append("|");
            sb.append(oldData.getUniqueRoadName());
            lineList.add(sb.toString());
        }

        File file = new File(fileName);
        FileUtils.writeLines(file, lineList);
    }

    private static List<OldAreaData> buildDbOldDataList(File dbFile) throws IOException {
        String FILE_ENCODING_TYPE = "cp949";

        // 우체국에서 다운받은 파일의 인코딩이 cp949이다.
        List<String> lines = FileUtils.readLines(dbFile, FILE_ENCODING_TYPE);
        log.info("loading {} finished. {} lines.", dbFile, lines.size());

        // 첫줄은 title이다. 버리자.
        lines.remove(0);

        return buildDbOldDataList(lines);

    }

    private static List<OldAreaData> buildDbOldDataList(List<String> lines) {

        List<OldAreaData> dbOldDataList = Lists.newArrayList();
        for (String line : lines) {
            dbOldDataList.add(new OldAreaData(line));
        }
        return dbOldDataList;
    }

    private String createDateLogDirectory() throws IOException {
        String BASE_LOG_DIRECTORY_NAME = "logs";

        Path LOG_DIRECTORY_PATH = Paths.get(BASE_LOG_DIRECTORY_NAME,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        if(Files.notExists(LOG_DIRECTORY_PATH)){
            Files.createDirectories(LOG_DIRECTORY_PATH);
        }

        return LOG_DIRECTORY_PATH + File.separator;
    }

}