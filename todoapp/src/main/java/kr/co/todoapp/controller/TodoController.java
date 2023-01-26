package kr.co.todoapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.todoapp.service.TodoService;
import kr.co.todoapp.vo.TodoVO;

@Controller
public class TodoController {

	@Autowired
	private TodoService service;
	
	@GetMapping("/list")
	public String list(Model model) {
		List<TodoVO> lists = service.selectTodos();
		List<TodoVO> listsC = service.selectTodosCompleted();
		model.addAttribute("lists", lists);
		model.addAttribute("listsC", listsC);
		return "/list";
	}
	
	@GetMapping("/write")
	public String write() {
		return "/write";
	}
	
	@PostMapping("/write")
	public String write(TodoVO vo) {
		service.insertTodo(vo);
		return "redirect:/list";
	}
	
	@GetMapping("/delete")
	public String delete(int no) {
		service.deleteTodo(no);
		return "redirect:/list";
	}
	
	@GetMapping("/modify")
	public String modify(@RequestParam("no") int no, Model model) {
		TodoVO list = service.selectTodo(no);
		model.addAttribute("list", list);
		return "/modify";
	}
	
	@ResponseBody
	@PostMapping("/modify")
	public Map<String, Integer> modify(TodoVO vo) {
		int result = service.updateTodo(vo);
		
		Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("result", result);
		
		return resultMap;
	}
	
	@ResponseBody
	@PostMapping("/completed")
	public Map<String, Integer> completed(TodoVO vo) {
		int result = service.completedTodo(vo);
		
		Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("result", result);
		
		return resultMap;
	}
	
	@ResponseBody
	@PostMapping("/incompleted")
	public Map<String, Integer> incompleted(TodoVO vo){
		int result = service.incompletedTodo(vo);
		
		Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("result", result);
		
		return resultMap;
	}
	
	
	
}
