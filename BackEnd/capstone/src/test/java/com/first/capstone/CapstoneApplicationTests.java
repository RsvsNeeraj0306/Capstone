package com.first.capstone;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = CapstoneApplication.class)
@AutoConfigureMockMvc
class CapstoneApplicationTests {

	@Test
	void 
	contextLoads() {
	}

}