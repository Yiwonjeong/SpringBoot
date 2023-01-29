package kr.co.farmstory.controller;

import kr.co.farmstory.entity.UserEntity;
import kr.co.farmstory.security.MyUserDetails;
import kr.co.farmstory.service.ArticleService;
import kr.co.farmstory.vo.ArticleVO;
import kr.co.farmstory.vo.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
public class BoardController {

    @Autowired
    private ArticleService service;

    // -------------------- 글 ----------------------
    // '글 등록하기' 화면
    @GetMapping("board/write")
    public String write(Model model, String group, String cate, @AuthenticationPrincipal MyUserDetails myUser)
    {
        UserEntity user = null;
        if(myUser != null){ user = myUser.getUser(); }
        model.addAttribute("user", user);

        model.addAttribute("group", group);
        model.addAttribute("cate", cate);
        return "board/write";
    }
    // 글 등록하기
    @PostMapping("board/write")
    public String write(ArticleVO vo, String group, String cate, HttpServletRequest req, @AuthenticationPrincipal MyUserDetails myUser){
        vo.setUid(myUser.getUser().getUid());
        vo.setRegip(req.getRemoteAddr());

        service.insertArticle(vo);
        return "redirect:/board/list?group="+group+"&cate="+cate;
    }
    // 글 목록 불러오기
    @GetMapping("board/list")
    public String list(@AuthenticationPrincipal MyUserDetails myUser, Model model, String group, String cate, String pg){

        UserEntity user = myUser.getUser();

        int currentPage = service.getCurrentPage(pg);
        int start = service.getLimitStart(currentPage);
        long total = service.getTotalCount();
        int lastPage = service.getLastPageNum(total);
        int pageStartNum = service.getPageStartNum(total, start);
        int groups[] = service.getPageGroup(currentPage, lastPage);

        List<ArticleVO> articles = service.selectArticles(cate, start);

        model.addAttribute("group", group);
        model.addAttribute("cate", cate);
        model.addAttribute("user", user);
        model.addAttribute("articles", articles);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("pageStartNum", pageStartNum);
        model.addAttribute("groups", groups);

        return "board/list";
    }
    // 글 불러오기
    @GetMapping("board/view")
    public String view(Model model, String group, String cate, int no, int pg, @AuthenticationPrincipal MyUserDetails myUser){
        UserEntity user = null;
        if(myUser != null){ user = myUser.getUser(); }
        model.addAttribute("user", user);

        model.addAttribute("group", group);
        model.addAttribute("cate", cate);

        // 글 조회수 +1
        service.updateArticleHit(no);
        ArticleVO article = service.selectArticle(no);
        model.addAttribute("article", article);
        model.addAttribute("pg", pg);

        return "board/view";
    }
    // 글 '수정하기' 화면
    @GetMapping("board/modify")
    public String modify(Model model, String group, String cate, int no, int pg, @AuthenticationPrincipal MyUserDetails myUser){
        UserEntity user = null;
        if(myUser != null){ user = myUser.getUser(); }
        model.addAttribute("user", user);

        model.addAttribute("group", group);
        model.addAttribute("cate", cate);

        ArticleVO article = service.selectArticle(no);
        model.addAttribute("article", article);
        model.addAttribute("pg", pg);

        return "board/modify";
    }
    // 글 수정하기
    @PostMapping("board/modify")
    public String modify(ArticleVO vo, String group, String cate, int no, int pg){
        service.updateArticle(vo);
        return "redirect:/board/view?group="+group+"&cate="+cate+"&no="+no+"&pg="+pg;
    }

    // 글 삭제하기
    @GetMapping("board/delete")
    public String delete(Model model, int no, String group, String cate, @AuthenticationPrincipal MyUserDetails myUser){
        UserEntity user = null;
        if(myUser != null){ user = myUser.getUser(); }
        model.addAttribute("user", user);

        model.addAttribute("group", group);
        model.addAttribute("cate", cate);

        service.deleteArticle(no);
        return "redirect:/board/list?group="+group+"&cate="+cate;
    }

    // --------------------  파일 -----------------------
    // 파일 다운로드
    @GetMapping("board/download")
    public ResponseEntity<Resource> download(int fno) throws IOException {
        FileVO vo = service.selectFile(fno);
        ResponseEntity<Resource> respEntity = service.fileDownload(vo);
        return respEntity;
    }

}
