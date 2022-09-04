package ru.adideas.backend_spring_media_api;

import org.hibernate.annotations.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BackendSpringMediaApiApplicationTests {

	@LocalServerPort
	private Integer port;

	@Test
	@Comment("This test will check the work of the helper")
	void testStaticFunctionIsJUnitTest() {
		assertTrue(BackendSpringMediaApiApplication.isJUnitTest());
	}

	@Test
	void contextLoads() {
		System.out.println(port);
	}

}
