package kr.co.ch10.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebMvcTest(controllers = MainController.class)
public class MainControllerTest {
	
	// MVC 목업 (MVC 테스트를 위한 가상 MVC 객체)
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void index() throws Exception {
		
		
		log.info("index...");
		/*
		mvc.perform(get("/index"))				// index 요청
		   .andExpect(status().isOk())			// HTTP요쳥 상태 테스트
		   .andExpect(view().name("/index"))	// view 이름
		   .andDo(print());  					// 요청 테스트 결과 출력
		*/
	}
	

}
