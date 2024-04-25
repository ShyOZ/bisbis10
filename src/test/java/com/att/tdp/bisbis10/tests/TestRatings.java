package com.att.tdp.bisbis10.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

import com.att.tdp.bisbis10.utility.ConfigureComplexTest;

@ConfigureComplexTest
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestRatings {
	private @Autowired MockMvc mockMvc;
	
	// TODO: implement ratings tests
}
