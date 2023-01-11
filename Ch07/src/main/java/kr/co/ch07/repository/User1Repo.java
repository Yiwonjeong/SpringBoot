package kr.co.ch07.repository;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.co.ch07.vo.User1VO;

@Repository
public interface User1Repo extends JpaRepository<User1VO, String>{
	
	// JPA 쿼리메소드 (정해진 이름이 있음)
	public User1VO findUser1VOByUid(String uid);
	public  List<User1VO> findUser1VOByName(String name);	//where `name`=?
	public List<User1VO> findUser1VOByNameNot(String name);	// where `name`!=?
	
	public User1VO findUser1VOByUidAndName(String uid, String name);	// where `uid`=? and `name`=?
	public List<User1VO> findUser1VOByUidOrName(String uid, String name);
	
	public List<User1VO> findUser1VOByAgeGreaterThan(int age);
	public List<User1VO> findUser1VOByAgeGreaterThanEqual(int age);
	public List<User1VO> findUser1VOByAgeLessThan(int age);
	public List<User1VO> findUser1VOByAgeLessThanEqual(int age);

	public List<User1VO> findUser1VOByNameLike(String name); // name의 앞 또는 뒤 % 명시
	public List<User1VO> findUser1VOByNameContains(String name);
	public List<User1VO> findUser1VOByNameStartsWith(String name); // name + %
	public List<User1VO> findUser1VOByNameEndsWith(String name); // % + name 
	
	// 정렬
	public List<User1VO> findUser1VOByOrderByName();
	public List<User1VO> findUser1VOByOrderByAgeAsc();
	public List<User1VO> findUser1VOByOrderByAgeDesc();
	public List<User1VO> findUser1VOByAgeGreaterThanOrderByAgeDesc(int age); 
	
	// count
	public int countUser1VOByUid(String uid);
	public int countUser1VOByName(String name);
	
	
	
	// JPQL (임의로 이름 정할 수 있음, u1 별칭 지어줘야 함)
	@Query("select u1 from User1VO as u1 where u1.age < 30")
	public List<User1VO> selectUserUnderAge30();
		
	@Query("select u1 from User1VO as u1 where u1.name = ?1")
	public List<User1VO> selectUserByName(String name);
	
	@Query("SELECT u1 FROM User1VO as u1 WHERE u1.name = :name")
	public List<User1VO> selectUserByNameWithParam(@Param("name") String name);
	
}
