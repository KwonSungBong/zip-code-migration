package com.example.demo.component;

import com.example.demo.service.RegionTableUpdater;
import com.example.demo.service.RegionWorkFileBuilder;
import com.example.demo.service.ZipCodeMappingTableUpdater;
import com.example.demo.service.ZipCodeMappingWorkFileBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("local")
//@Rollback(false)
public class AreaIntegrationTest {

    @Autowired
    private RegionWorkFileBuilder regionWorkFileBuilder;

    @Autowired
    private RegionTableUpdater regionTableUpdater;

    @Autowired
    private ZipCodeMappingWorkFileBuilder zipCodeMappingWorkFileBuilder;

    @Autowired
    private ZipCodeMappingTableUpdater zipCodeMappingTableUpdater;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void buildRegion() throws IOException {
        regionWorkFileBuilder.build();

        log.debug("- - - - - test complete - - - - -");
    }

    @Test
    public void updateRegion() throws IOException {
        regionTableUpdater.update();

        log.debug("- - - - - test complete - - - - -");
    }

    @Test
    public void buildZipCodeMapping() throws IOException {
        zipCodeMappingWorkFileBuilder.build();

        log.debug("- - - - - test complete - - - - -");
    }

    @Test
    public void updateZipCodeMapping() throws IOException {
        zipCodeMappingTableUpdater.update();

        log.debug("- - - - - test complete - - - - -");
    }

}