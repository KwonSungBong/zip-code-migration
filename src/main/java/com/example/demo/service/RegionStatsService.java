package com.example.demo.service;

import com.example.demo.dto.RegionStatsDto;
import com.example.demo.entity.QRegion;
import com.example.demo.entity.QRegionStats;
import com.example.demo.entity.RegionStats;
import com.example.demo.repository.RegionStatsRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by ksb on 2018. 2. 3..
 */
@Service
public class RegionStatsService {

    private final static QRegionStats qRegionStats = QRegionStats.regionStats;
    private final static QRegion qRegion = QRegion.region;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RegionStatsRepository regionStatsRepository;

    public List<RegionStats> findAll() {
        List<RegionStats> regionStatsList = new JPAQueryFactory(entityManager)
                .selectFrom(qRegionStats)
                .leftJoin(qRegionStats.region1, qRegion)
                .fetchJoin()
                .leftJoin(qRegionStats.region2, qRegion)
                .fetchJoin()
                .leftJoin(qRegionStats.region3, qRegion)
                .fetchJoin()
                .fetch();

        return regionStatsList;
    }

    public List<RegionStatsDto> selectRegionStatsDtoList() {
        return regionStatsRepository.selectRegionStatsDtoList();
    }

    @Transactional
    public void migration() {
        regionStatsRepository.migration();
    }

}
