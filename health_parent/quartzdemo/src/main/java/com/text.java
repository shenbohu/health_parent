package com;

import com.itheima.utils.MD5Utils;
import com.itheima.utils.SMSUtils;
import org.aspectj.weaver.ast.Var;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;

public class text {
    public static void main(String[] args)throws Exception {
//        SMSUtils.sendShortMessage("SMS_175061136","17391950812","1234");
        String s = MD5Utils.md5("1234");
        String s1 = DigestUtils.md5DigestAsHex("1234".getBytes());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("1234");
        System.out.println(encode);
        System.out.println(s);
        System.out.println(s1);
    }
}
