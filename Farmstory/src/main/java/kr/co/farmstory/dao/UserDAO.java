package kr.co.farmstory.dao;

import kr.co.farmstory.vo.TermsVO;
import kr.co.farmstory.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDAO {
    public int insertUser(UserVO vo);
    public UserVO insertKakaoUser(UserVO vo);
    public UserVO selectUser(String uid);
    public List<UserVO> selectUsers();
    public TermsVO selectTerms();
    public int updateUser(UserVO vo);
    public int deleteUser(String uid);

}
