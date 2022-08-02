package com.seo.myblog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyblogApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void myTest(){
		String text = "/attached/2022/08/01/b2470ba8-1ca5-4da9-b749-fd5d7a92a016.pdf";
		String splitText = text.split("attached")[1];
		System.out.println(splitText);
	}

}
