package kr.co.sboard.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.sboard.entity.UserEntity;
import kr.co.sboard.security.MyUserDetails;
import kr.co.sboard.service.ArticleService;
import kr.co.sboard.vo.ArticleVO;
import kr.co.sboard.vo.FileVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ArticleController {
	
	@Autowired
	private ArticleService service;

	// ---------- 글 ------------
	// '글 등록하기' 화면
	@GetMapping("write")
	public String write() {
		return "write";
	}
	
	// 글 등록하기
	@PostMapping("write")
	public String write(ArticleVO vo) {
		service.insertArticle(vo);
		return "redirect:/list";
	}
	
	// 글 목록 불러오기
	@GetMapping("list")
	public String list(@AuthenticationPrincipal MyUserDetails myUser, Model model, String pg) {
		// principal : security가 저장하는 사용자 객체 (myUser)
		
		UserEntity user = myUser.getUser();
		//log.info(user.toString());
		
		int currentPage = service.getCurrentPage(pg);
		int start = service.getLimitStart(currentPage);
		long total = service.getTotalCount();
		int lastPage = service.getLastPageNum(total);
		int pageStartNum = service.getPageStartNum(total, start);
		int groups[] = service.getPageGroup(currentPage, lastPage);
		
		List<ArticleVO> articles = service.selectArticles(start);
		
		model.addAttribute("user", user);
		model.addAttribute("articles", articles);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("pageStartNum", pageStartNum);
		model.addAttribute("groups", groups);
		
		return "list";
	}
	
	// 글 불러오기
	@GetMapping("view")
	public String view(@RequestParam("no") int no, Model model, int pg) {
		service.updateArticleHit(no);
		
		ArticleVO article = service.selectArticle(no);
		model.addAttribute("article", article);
		model.addAttribute("pg", pg);
		return "view";
	}
	
	// 글 '수정하기' 화면
	@GetMapping("modify")
	public String modify(@RequestParam("no") int no, Model model, int pg) {
		ArticleVO article = service.selectArticle(no);
		model.addAttribute("article", article);
		model.addAttribute("pg", pg);
		return "modify";
	}
	
	// 글 수정하기
	@PostMapping("modify")
	public String modify(ArticleVO vo) {
		service.updateArticle(vo);
		return "redirect:/view?no="+vo.getNo()+"&pg="+vo.getPg();
	}
	
	// 글 삭제하기
	@GetMapping("delete")
	public String delete(int pg, int no) {
		service.deleteArticle(no);
		return "redirect:/list?pg="+pg;
	}
	
	// ----------  파일 -------------
	// 파일 다운로드
	@GetMapping("download")
	public ResponseEntity<Resource> download(int fno) throws IOException {
		FileVO vo = service.selectFile(fno);
		ResponseEntity<Resource> respEntity = service.fileDownload(vo);
		return respEntity;
	}
	
	
	
	
	
	
}
