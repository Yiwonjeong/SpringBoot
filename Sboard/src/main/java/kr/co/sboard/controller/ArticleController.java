package kr.co.sboard.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

	// -------------------- 글 ----------------------
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
		
		// 글 조회수 +1
		service.updateArticleHit(no);
		List<ArticleVO> comments = service.selectComment(no);
		ArticleVO article = service.selectArticle(no);
		
		model.addAttribute("article", article);
		model.addAttribute("pg", pg);
		model.addAttribute("comments", comments);
		
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
	
	// --------------------  파일 -----------------------
	// 파일 다운로드
	@GetMapping("download")
	public ResponseEntity<Resource> download(int fno) throws IOException {
		FileVO vo = service.selectFile(fno);
		ResponseEntity<Resource> respEntity = service.fileDownload(vo);
		return respEntity;
	}
	
	// --------------------  댓글 -----------------------
	// 댓글 작성
	@PostMapping("insertComment")
	public String insertComment(ArticleVO vo, HttpServletRequest req, int parent) {
		vo.setRegip(req.getRemoteAddr());
		
		// 댓글 수 + 1 (목록 댓글 수 표기 위함)
		service.updateCommentPlus(parent);
		service.insertComment(vo);
		
		return "redirect:/view?no="+vo.getParent()+"&pg="+vo.getPg();
	}
	
	// 댓글 목록 - 위 글 보기(view)에서 ... 
	
	// 댓글 삭제
	@GetMapping("deleteComment")
	public String deleteComment(ArticleVO vo, int no, int pg, int parent) {
		service.updateCommentMinus(parent);
		service.deleteComment(no);
		return "redirect:/view?no="+vo.getNo()+"&pg="+vo.getPg();
	}
	
	@GetMapping("updateComment")
	public String updateComment(ArticleVO vo, int no, int pg) {
		return "redirect:/view?no="+vo.getParent()+"&pg="+vo.getPg();
	}
	
	// 댓글 수정
	@ResponseBody
	@PostMapping("updateComment")
	public Map<String, Integer> updateComment(ArticleVO vo, HttpServletRequest req) {
		
		vo.setRegip(req.getRemoteAddr());
		
		int result = service.updateComment(vo);
		
		Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("result", result);
		
		
		return resultMap;
	}
	
	
}
