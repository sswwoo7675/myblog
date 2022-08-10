package com.seo.myblog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class MyblogApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void myTest(){
		List<String> a = new LinkedList<>();
		a.add("1");
		a.add("2");
		a.add("3");
		a.add("4");
		a.add("5");

		Iterator it= a.iterator();


	}

}
