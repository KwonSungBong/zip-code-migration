package com.example.demo.component;

import com.example.demo.service.RegionWorkFileBuilder;
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
public class ZipCodeMappingIntegrationTest {

    @Autowired
    RegionWorkFileBuilder regionWorkFileBuilder;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() throws IOException {
        regionWorkFileBuilder.build();

        log.debug("- - - - - test complete - - - - -");
    }

    @Test
    public void testtest() throws IOException {
        String test = "";

        log.debug("- - - - - test complete - - - - -");
    }

}