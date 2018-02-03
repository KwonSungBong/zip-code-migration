package com.example.demo.repository;

import com.example.demo.dto.RegionStatsDto;
import com.example.demo.entity.RegionStats;
import com.example.demo.entity.RegionStatsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionStatsRepository extends JpaRepository<RegionStats, RegionStatsId> {

    @Query(name = "selectRegionStatsDtoList")
    List<RegionStatsDto> selectRegionStatsDtoList();

    @Modifying(clearAutomatically = true)
    @Query(name = "migration")
    int migration();

}
