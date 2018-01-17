package com.example.demo.component;

import com.example.demo.service.AreaTableMaker;
import com.example.demo.service.RegionRawTableMaker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TableMakerTests {

	@Autowired
	private AreaTableMaker areaTableMaker;

	@Autowired
	private RegionRawTableMaker regionRawTableMaker;

	@Test
	public void test() throws IOException {
		System.out.println();
	}

	@Test
	public void initNewAreaTable() throws IOException {
		areaTableMaker.initNewAreaTable();
	}

	@Test
	public void initOldAreaTable() throws IOException {
		areaTableMaker.initOldAreaTable();
	}

	@Test
	public void initRangeAreaTable() throws IOException {
		areaTableMaker.initRangeAreaTable();
	}

	@Test
	public void initRegionRawTable() throws IOException {
		regionRawTableMaker.initRegionRawTable();
	}

}
