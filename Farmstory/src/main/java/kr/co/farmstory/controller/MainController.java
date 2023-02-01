package kr.co.farmstory.controller;

import kr.co.farmstory.service.ArticleService;
import kr.co.farmstory.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ArticleService service;

    @GetMapping(value={"/", "index"})
    public String index(Model model, String cate){

       List<ArticleVO> articles = service.selectIndexArticles();
       List<ArticleVO> articles2 = service.selectIndexArticles2();

       model.addAttribute("articles", articles);
       model.addAttribute("articles2", articles2);

       return "index";
    }
}
