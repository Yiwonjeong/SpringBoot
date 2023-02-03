package kr.co.farmstory.service;

import kr.co.farmstory.dao.UserDAO;
import kr.co.farmstory.repo.UserRepo;
import kr.co.farmstory.vo.TermsVO;
import kr.co.farmstory.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserDAO dao;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepo repo;

    public int insertUser(UserVO vo) {
        // 패스워드 암호화
        vo.setPass(encoder.encode(vo.getPass1()));
        return dao.insertUser(vo);
    }

    public UserVO insertKakaoUser(UserVO vo) {
        return dao.insertKakaoUser(vo);
    }

    public UserVO selectUser(String uid) {
        return dao.selectUser(uid);
    }

    public List<UserVO> selectUsers() {
        return dao.selectUsers();
    }

    public TermsVO selectTerms() {
        return dao.selectTerms();
    }

    public int updateUser(UserVO vo) {
        return dao.updateUser(vo);
    }

    public int deleteUser(String uid) {
        return dao.deleteUser(uid);
    }

    public int countByUid(String uid) {
        return repo.countByUid(uid);
    }

    public int countByNick(String nick) {
        return repo.countByNick(nick);
    }



}
