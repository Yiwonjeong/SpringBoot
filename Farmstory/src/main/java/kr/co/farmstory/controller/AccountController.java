package kr.co.farmstory.controller;

import kr.co.farmstory.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final MailService mailService;

    @ResponseBody
    @GetMapping("login/mailConfirm")
    public Map<String, String> mailConfirm(@RequestParam("email") String email) throws Exception {

        log.warn("here1 email : "+email);

       String code = mailService.sendSimpleMessage(email);
       log.warn("인증코드: "+code);

       Map<String, String> resultMap = new HashMap<>();
       resultMap.put("result", code);

       return resultMap;
    }

}
