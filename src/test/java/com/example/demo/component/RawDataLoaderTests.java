package com.example.demo.component;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RawDataLoaderTests {

	@Test
	public void load() throws IOException {
		String rawDataFileName = "data/강원도.txt";

		RawDataLoader.load(rawDataFileName);

	}

}
