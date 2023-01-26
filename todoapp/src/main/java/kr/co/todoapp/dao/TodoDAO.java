package kr.co.todoapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.todoapp.vo.TodoVO;

@Mapper
@Repository
public interface TodoDAO {

	public int insertTodo(TodoVO vo);
	public List<TodoVO> selectTodos();
	public List<TodoVO> selectTodosCompleted();
	public TodoVO selectTodo(int no);
	public int updateTodo(TodoVO vo);
	public int completedTodo(TodoVO vo);
	public int incompletedTodo(TodoVO vo);
	public int deleteTodo(int no);
	
}
