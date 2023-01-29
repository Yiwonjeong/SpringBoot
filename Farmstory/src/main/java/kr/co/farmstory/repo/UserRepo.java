package kr.co.farmstory.repo;

import kr.co.farmstory.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, String> {
    public int countByUid(String uid);
    public int countByNick(String nick);
}
