package kr.co.farmstory.repo;

import kr.co.farmstory.entity.UserEntity;
import kr.co.farmstory.vo.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, String> {
    public int countByUid(String uid);
    public int countByNick(String nick);
}
