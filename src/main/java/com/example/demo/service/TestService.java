package com.example.demo.service;

import com.example.demo.dto.RawData;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by whilemouse on 17. 11. 16.
 */
public class TestService {

    private static final String[] dbFileExtensions = {"txt"};
    public static String FILE_ENCODING_TYPE = "cp949";

    public static List<RawData> load(String dbFolderName, Map<String, List<String>> zipCode2BuildingListMap, Map<String, String> zipCode2UniqueRoadNameMap) throws IOException {

        List<RawData> dbDataList = Arrays.asList();

        File dbFolder = new File(dbFolderName);
        Iterator<File> dbFiles = FileUtils.iterateFiles(dbFolder, dbFileExtensions, true);
        while (dbFiles.hasNext()) {
            File dbFile = dbFiles.next();
            List<RawData> aDbDataList = buildDbDataList(dbFile);
            aDbDataList = filterOutAndStoreBuildingAndUniqueRoadName(aDbDataList, zipCode2BuildingListMap, zipCode2UniqueRoadNameMap);
            dbDataList.addAll(aDbDataList);
        }

        return dbDataList;
    }

//    @VisibleForTesting
    public static List<RawData> filterOutAndStoreBuildingAndUniqueRoadName(List<RawData> aDbDataList, Map<String, List<String>> zipCode2BuildingListMap, Map<String, String> zipCode2UniqueRoadNameMap) {

        List<RawData> dbDataList = Arrays.asList();
        for (RawData aDbData : aDbDataList) {
            addZipCode2UniqueRoadNameMap(zipCode2UniqueRoadNameMap, aDbData);

            if (StringUtils.isNotBlank(aDbData.getBuildingName())) {
                addZipcode2BuildingListMap(zipCode2BuildingListMap, aDbData);
            } else {
                dbDataList.add(aDbData);
            }
        }

        return dbDataList;
    }

    private static void addZipcode2BuildingListMap(Map<String, List<String>> zipCode2BuildingListMap, RawData dbData) {

        List<String> buildingList = zipCode2BuildingListMap.get(dbData.getZipCode());

        if (buildingList == null) {
            buildingList = Arrays.asList();
        }

        if (!buildingList.contains(dbData.getBuildingName())) {
            buildingList.add(dbData.getBuildingName());
        }

        zipCode2BuildingListMap.put(dbData.getZipCode(), buildingList);
    }

    private static void addZipCode2UniqueRoadNameMap(Map<String, String> zipCode2UniqueRoadNameMap, RawData dbData) {
        // zipcode 당 1번만 도로명을 맵에 추가
        String uniqueRoadName = zipCode2UniqueRoadNameMap.get(dbData.getZipCode());
        if(uniqueRoadName == null){
            zipCode2UniqueRoadNameMap.put(dbData.getZipCode(), dbData.getUniqueRoadName());
        }
    }

    private static List<RawData> buildDbDataList(File dbFile) throws IOException {
        // 우체국에서 다운받은 파일의 인코딩이 cp949이다.
        List<String> lines = FileUtils.readLines(dbFile, FILE_ENCODING_TYPE);

        // 첫줄은 title이다. 버리자.
        lines.remove(0);

        return buildDbDataList(lines);
    }

    private static List<RawData> buildDbDataList(List<String> lines) {

        List<RawData> dbDataList = new ArrayList();
        for (String line : lines) {
            dbDataList.add(new RawData(line));
        }
        return dbDataList;
    }


}
