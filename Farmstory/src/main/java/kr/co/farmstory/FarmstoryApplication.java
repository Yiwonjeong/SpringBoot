package kr.co.farmstory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@SpringBootApplication
public class FarmstoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmstoryApplication.class, args);
	}




}
