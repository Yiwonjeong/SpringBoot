package kr.co.sboard.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ArticleServiceTest {
	
	@Autowired
	private ArticleService service;
	
	// 파일 경로 테스트
	@Test
	public void test1() {
		//service.fileUploade();
	}

}
