package com.example.demo.component;

import com.example.demo.dto.AreaRawData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RawDataLoader {

    public static String FILE_ENCODING_TYPE = "cp949";

    public static List<AreaRawData> load(String fileName) throws IOException {

        File dataFile = new File(fileName);

        // 우체국에서 다운받은 파일의 인코딩이 cp949이다.
        List<String> lines = FileUtils.readLines(dataFile, FILE_ENCODING_TYPE);
        log.info("rawdata file {} loading...",fileName);

        // 첫줄은 title이다. 버리자.
        lines.remove(0);

        List<AreaRawData> rawDataList = new ArrayList();
        int cnt = 0;
        for(String line : lines) {
            rawDataList.add(new AreaRawData(line));
            cnt ++;
        }

        log.info(" Area raw data size : {} ",cnt);

        return rawDataList;

    }
}
