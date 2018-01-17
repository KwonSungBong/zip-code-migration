package com.example.demo.service;

import com.example.demo.component.IdGenerator;
import com.example.demo.dto.RegionRaw;
import com.example.demo.entity.Region;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by ksb on 2018. 1. 15..
 */
@Slf4j
@Service
public class RegionRawBuilder {

    public static final Integer ROOT_DEPTH = 0;
    public static final String ROOT_NAME = "root";

    public static final String ETC_NAME = "etc";
    public static final Integer ETC_DEPTH = 1;

    public static final Integer BEST_DEPTH = 1;

    public static final Integer DEPTH1 = 1;
    public static final Integer DEPTH2 = 2;
    public static final Integer DEPTH3 = 3;
    public static final Integer DEPTH4 = 4;
    public static final Integer DEPTH5 = 5;

    @Autowired
    private IdGenerator idGenerator;

    public List<Region> build(List<RegionRaw> regionRawList) {
        List<Region> regionList = Lists.newArrayList();

        Map<String, RegionRaw> depth1RawMap = Maps.newHashMap();
        Map<String, RegionRaw> depth2RawMap = Maps.newHashMap();
        Map<String, RegionRaw> depth3RawMap = Maps.newHashMap();
        Map<String, RegionRaw> depth4RawMap = Maps.newHashMap();
        Map<String, RegionRaw> depth5RawMap = Maps.newHashMap();

        for (RegionRaw regionRaw : regionRawList) {
            depth1RawMap.put(regionRaw.getFullName(DEPTH1), regionRaw);
            depth2RawMap.put(regionRaw.getFullName(DEPTH2), regionRaw);
            depth3RawMap.put(regionRaw.getFullName(DEPTH3), regionRaw);
            depth4RawMap.put(regionRaw.getFullName(DEPTH4), regionRaw);
            depth5RawMap.put(regionRaw.getFullName(DEPTH5), regionRaw);
        }

        // root 생성
        Region rootRegion = new Region();
        rootRegion.setId(idGenerator.getNewId());
        rootRegion.setName(ROOT_NAME);
        rootRegion.setDepth(ROOT_DEPTH);
        regionList.add(rootRegion);

        // etc 생성
        Region etcRegion = new Region();
        etcRegion.setId(idGenerator.getNewId());
        etcRegion.setParent(rootRegion.getId());
        etcRegion.setName(ETC_NAME);
        etcRegion.setDepth(ETC_DEPTH);
        etcRegion.setDepth1(etcRegion.getId());
        etcRegion.setDepth1Name(etcRegion.getDepth1Name());
        regionList.add(etcRegion);

        // depth1 생성
        Map<String, Region> depth1Map = Maps.newHashMap();
        depth1RawMap.forEach((fullName, regionRaw) -> {
            Region region = new Region();
            region.setId(idGenerator.getNewId());
            region.setParent(rootRegion.getId());
            region.setName(regionRaw.getName(DEPTH1));
            region.setZipCode(regionRaw.getZipCode());
            region.setDepth(DEPTH1);
            region.setDepth1(region.getId());
            region.setDepth1Name(region.getName());

            regionRawList.add(regionRaw);
            depth1Map.put(region.getFullName(), region);
        });

        // depth2 생성
        Map<String, Region> depth2Map = Maps.newHashMap();
        depth2RawMap.forEach((fullName, regionRaw) -> {
            Region parent = depth1Map.get(regionRaw.getParentFullName(DEPTH2));

            Region region = new Region();
            region.setId(idGenerator.getNewId());
            region.setParent(parent.getId());
            region.setName(regionRaw.getName(DEPTH2));
            region.setZipCode(regionRaw.getZipCode());
            region.setDepth(DEPTH2);
            region.setDepth1(parent.getDepth1());
            region.setDepth1Name(parent.getDepth1Name());
            region.setDepth2(region.getId());
            region.setDepth2Name(region.getName());
            regionList.add(region);
            depth2Map.put(region.getFullName(), region);
        });

        // depth3 생성
        Map<String, Region> depth3Map = Maps.newHashMap();
        depth3RawMap.forEach((fullName, regionRaw) -> {
            Region parent = depth2Map.get(regionRaw.getParentFullName(DEPTH3));

            Region region = new Region();
            region.setId(idGenerator.getNewId());
            region.setParent(parent.getId());
            region.setName(regionRaw.getName(DEPTH3));
            region.setZipCode(regionRaw.getZipCode());
            region.setDepth(DEPTH3);
            region.setDepth1(parent.getDepth1());
            region.setDepth1Name(parent.getDepth1Name());
            region.setDepth2(parent.getDepth2());
            region.setDepth2Name(parent.getDepth2Name());
            region.setDepth3(region.getId());
            region.setDepth3Name(region.getName());
            regionList.add(region);
            depth3Map.put(region.getFullName(), region);
        });

        // depth4 생성
        Map<String, Region> depth4Map = Maps.newHashMap();
        depth4RawMap.forEach((fullName, regionRaw) -> {
            Region parent = depth3Map.get(regionRaw.getParentFullName(DEPTH4));

            Region region = new Region();
            region.setId(idGenerator.getNewId());
            region.setParent(parent.getId());
            region.setName(regionRaw.getName(DEPTH4));
            region.setZipCode(regionRaw.getZipCode());
            region.setDepth(DEPTH4);
            region.setDepth1(parent.getDepth1());
            region.setDepth1Name(parent.getDepth1Name());
            region.setDepth2(parent.getDepth2());
            region.setDepth2Name(parent.getDepth2Name());
            region.setDepth3(parent.getDepth3());
            region.setDepth3Name(parent.getDepth3Name());
            region.setDepth4(region.getId());
            region.setDepth4Name(region.getName());
            regionList.add(region);
            depth4Map.put(region.getFullName(), region);
        });

        // depth5 생성
        depth5RawMap.forEach((fullName, regionRaw) -> {
            Region parent = depth4Map.get(regionRaw.getParentFullName(DEPTH5));

            Region region = new Region();
            region.setId(idGenerator.getNewId());
            region.setParent(parent.getId());
            region.setName(regionRaw.getName(DEPTH5));
            region.setZipCode(regionRaw.getZipCode());
            region.setDepth(DEPTH5);
            region.setDepth1(parent.getDepth1());
            region.setDepth1Name(parent.getDepth1Name());
            region.setDepth2(parent.getDepth2());
            region.setDepth2Name(parent.getDepth2Name());
            region.setDepth3(parent.getDepth3());
            region.setDepth3Name(parent.getDepth3Name());
            region.setDepth4(parent.getDepth3());
            region.setDepth4Name(parent.getDepth3Name());
            region.setDepth5(region.getId());
            region.setDepth5Name(region.getName());
            regionList.add(region);
        });

        return regionList;
    }

}
