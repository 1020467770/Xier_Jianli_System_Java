package cn.sqh.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderUtils {

    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public static void main(String[] args) {
        String password = "123";
        System.out.println(encodePassword(password));//$2a$10$b1CDliZzPijH10jeGJo3hO/qqtkW.zQS.aPnVw2KYMR9Ev.f/.KI2

        boolean matches = bCryptPasswordEncoder.matches(password, "$2a$10$b1CDliZzPijH10jeGJo3hO/qqtkW.zQS.aPnVw2KYMR9Ev.f/.KI2");
        System.out.println(matches);
    }
}
