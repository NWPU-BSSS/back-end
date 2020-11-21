package com.nwpu.bsss.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class BlogControllerTest {
	
	@Resource
	private RestTemplate restTemplate;
	private static final String PREFIX = "localhost:8080/blog/";
	
	@Test
	void getComments() {
		Map<String, String> map = new HashMap<>();
		map.put("blogId", String.valueOf(1L));
		System.out.println(this.restTemplate.getForObject(PREFIX + "comments", ArrayList.class, map));
	}
}