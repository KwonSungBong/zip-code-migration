package com.example.demo.component;

import com.example.demo.dto.RegionStatsDto;
import com.example.demo.entity.RegionStats;
import com.example.demo.service.RegionStatsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("local")
//@Rollback(false)
public class RegionStatsServiceTest {

    @Autowired
    private RegionStatsService regionStatsService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findAll() throws IOException {
        List<RegionStats> regionStatsList = regionStatsService.findAll();

        log.debug("- - - - - test complete - - - - -");
    }

    @Test
    public void selectRegionStatsDtoList() throws IOException {
        List<RegionStatsDto> regionStatsList = regionStatsService.selectRegionStatsDtoList();

        log.debug("- - - - - test complete - - - - -");
    }

    @Test
    public void migrationRegionStats() throws IOException {
        regionStatsService.migration();

        log.debug("- - - - - test complete - - - - -");
    }

}