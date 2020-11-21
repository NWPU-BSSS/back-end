package com.nwpu.bsss.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// FIXME: 11/21/2020 lzj 谁能教教咋测试鸭
class BlogControllerTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	private String PREFIX;
	
	@BeforeEach
	public void setup() {
		this.PREFIX = "http://localhost:" + this.port + "/api/blog/";
	}
	
	@Test
	public void getComments() {
		Map<String, String> map = new HashMap<>();
		map.put("blogId", String.valueOf(1L));
		System.out.println(this.restTemplate.getForEntity(this.PREFIX + "comments", JSONPObject.class, map));
	}
}