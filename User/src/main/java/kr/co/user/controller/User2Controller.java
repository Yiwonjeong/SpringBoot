package kr.co.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.user.service.User2Service;
import kr.co.user.vo.User2VO;


@Controller
public class User2Controller {

	@Autowired
	private User2Service service;
	
	@GetMapping("/user2/list")
	public String list(Model model) {
		List<User2VO> users = service.selectUser2s();
		model.addAttribute("users", users);
		return "/user2/list";
	}
	
	@GetMapping("/user2/register")
	public String register() {
		return "/user2/register";
	}
	
	@PostMapping("/user2/register")
	public String register(User2VO vo) {
		service.insertUser2(vo);
		return "redirect:/user2/list";
	}
	
	@GetMapping("/user2/modify")
	public String modify(String uid, Model model) {
		User2VO user = service.selectUser2(uid);
		model.addAttribute("user", user);
		return "/user2/modify";
	}
	@PostMapping("/user2/modify")
	public String modify(User2VO vo) {
		service.updateUser2(vo);
		return "redirect:/user2/list";
	}
	
	@GetMapping("/user2/delete")
	public String delete(String uid) {
		service.deleteUser2(uid);
		return "redirect:/user2/list";
	}
	
}