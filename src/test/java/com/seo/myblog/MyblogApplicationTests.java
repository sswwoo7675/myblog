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
		String aa = "테스트입니다\r\n아아아\r\n\r\n반갑";

		String newText = aa.replace("\r\n","-");

		System.out.println(newText);
	}

}
