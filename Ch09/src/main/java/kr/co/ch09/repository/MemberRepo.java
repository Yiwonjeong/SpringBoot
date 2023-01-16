package kr.co.ch09.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.ch09.vo.MemberVO;

@Repository
public interface MemberRepo extends JpaRepository<MemberVO, String>{

}
