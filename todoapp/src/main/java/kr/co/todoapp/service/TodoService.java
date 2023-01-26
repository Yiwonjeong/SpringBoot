package kr.co.todoapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.todoapp.dao.TodoDAO;
import kr.co.todoapp.vo.TodoVO;

@Service
public class TodoService {

	@Autowired
	private TodoDAO dao;
	
	public int insertTodo(TodoVO vo) {
		int result = dao.insertTodo(vo);
		return result;
	}
	public List<TodoVO> selectTodos(){
		return dao.selectTodos();
	}
	public List<TodoVO> selectTodosCompleted(){
		return dao.selectTodosCompleted();
	}
	public TodoVO selectTodo(int no) {
		TodoVO vo = dao.selectTodo(no);
		return vo;
	}
	public int updateTodo(TodoVO vo) {
		int result = dao.updateTodo(vo);
		return result;
	}
	public int completedTodo(TodoVO vo) {
		int result= dao.completedTodo(vo);
		return result;
	}
	public int incompletedTodo(TodoVO vo) {
		int result = dao.incompletedTodo(vo);
		return result;
	}
	public int deleteTodo(int no) {
		return dao.deleteTodo(no);
	}
	
}
