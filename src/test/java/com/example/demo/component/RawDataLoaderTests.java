package com.example.demo.component;

import com.example.demo.entity.TestEntity;
import com.example.demo.repository.TestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RawDataLoaderTests {

	@Test
	public void load() throws IOException {
		String rawDataFileName = "data/강원도.txt";

		RawDataLoader.load(rawDataFileName);

	}

	@Autowired
	TestRepository testRepository;

	@Test
	@Rollback(false)
	public void test() {
		TestEntity testEntity = new TestEntity();
		testEntity.setTest("test");
		testRepository.save(testEntity);
	}

	@Test
	@Rollback(false)
	public void test2() {
		TestEntity testEntity = testRepository.findOne(1L);
		test3(testEntity);
	}

	@Transactional
	public void test3(TestEntity testEntity) {
		TestEntity clone = testEntity.clone(true);
		testRepository.save(clone);
	}

}
