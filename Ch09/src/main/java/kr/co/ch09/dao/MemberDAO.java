package kr.co.ch09.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.ch09.vo.MemberVO;

@Mapper
@Repository
public interface MemberDAO {
	
	public int insertMember(MemberVO vo);
	public MemberVO selectMember(String id);
	public List<MemberVO> selectMembers();
	public int updateMember(MemberVO vo);
	public int deleteMember(String id);

}
