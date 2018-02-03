package com.example.demo.service;

import com.example.demo.dto.AreaRaw;
import com.example.demo.entity.ZipCodeMapping;
import com.example.demo.exception.ZipCodeMappingFailException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by ksb on 2018. 2. 3..
 */
@Service
public class ZipCodeMappingRawBuilder {

    public List<ZipCodeMapping> build(List<AreaRaw> oldAreaRawList, List<AreaRaw> newAreaRawList){
        Map<String, List<AreaRaw>> newZipCodeRawMap = newAreaRawList.stream()
                .distinct().collect(Collectors.groupingBy(AreaRaw::mappingKey));

        Map<String, List<AreaRaw>> oldZipCodeRawMap = oldAreaRawList.stream()
                .distinct().collect(Collectors.groupingBy(AreaRaw::getZipCode));

        List<ZipCodeMapping> zipCodeMappingList = Lists.newArrayList();
        Map<String, List<AreaRaw>> failZipCodeMappingMap = Maps.newHashMap();

        oldZipCodeRawMap.forEach((key, value) -> {
            int i = 0;
            for(AreaRaw oldZipCodeRaw : value) {
                i++;
                List<AreaRaw> list = newZipCodeRawMap.get(oldZipCodeRaw.mappingKey());
                if(list == null) {
                    continue;
                }

                AreaRaw newZipCodeRaw = list.stream().reduce((first, second) -> second).orElse(null);

                if(newZipCodeRaw != null) {
                    zipCodeMappingList.add(new ZipCodeMapping(key, newZipCodeRaw.getZipCode()));
                    i--;
                    break;
                }
            }

            if(i == value.size()) {
                failZipCodeMappingMap.put(key, value);
            }
        });

//        if(failZipCodeMappingMap.size() > 0) {
//            String message = "fail size : " + failZipCodeMappingMap.size();
//            throw new ZipCodeMappingFailException(message);
//        }

        return zipCodeMappingList;
    }

}
