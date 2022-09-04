package ru.adideas.backend_spring_media_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BackendSpringMediaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendSpringMediaApiApplication.class, args);
	}

	public static boolean isJUnitTest() {
		for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
			if (element.getClassName().startsWith("org.junit.")) {
				return true;
			}
		}
		return false;
	}
}
