package com.example.demo.component;

import com.example.demo.entity.Region;
import com.example.demo.query.RegionQuery;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by ksb on 2018. 1. 15..
 */
@Component
public class IdFinder {

    private final Map<String, Integer> fullNameIdMap = Maps.newHashMap(); // fullPath -> id

    private final Map<String, Integer> fullNameParentIdMap = Maps.newHashMap(); // fullPath -> parentId

    @Autowired
    private RegionQuery regionQuery;

    // 기존 데이타의 id를 보존하기 위하여 id를 구한다.
    public void load() {
        List<Region> resultList = regionQuery.loadRegion();
        for (Region region : resultList) {
            Integer id = region.getId();
            String fullName = region.getFullName();
            Integer parentId = region.getParent();

            putFullNameIdMap(fullName, id);
            putFullNameParentIdMap(fullName, parentId);
        }
    }

    public Integer getFullNameIdMap(String fullName) {
        return fullNameIdMap.get(fullName);
    }

    public Integer getFullNameParentIdMap(String fullName) {
        return fullNameParentIdMap.get(fullName);
    }

    public void putFullNameIdMap(String fullName, int id) {
        fullNameIdMap.put(fullName, id);
    }

    public void putFullNameParentIdMap(String fullName, Integer parentId) {
        fullNameParentIdMap.put(fullName, parentId);
    }

}
