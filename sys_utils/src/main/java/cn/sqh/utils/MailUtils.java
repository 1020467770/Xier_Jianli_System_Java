package cn.sqh.utils;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Properties;

/**
 * 发送邮件工具类
 */
public final class MailUtils {

    private static String USER = "";
    private static String PASSWORD = "";

    public static boolean sendMail(String to, String text, String title){
        try {
            Properties pro=new Properties();

            ClassLoader classLoader = MailUtils.class.getClassLoader();
            InputStream ras = classLoader.getResourceAsStream("email.properties");
            pro.load(ras);
            USER=pro.getProperty("username");
            PASSWORD=pro.getProperty("password");

            final Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.qq.com");

            props.put("mail.user", USER);
            props.put("mail.password", PASSWORD);


            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };

            Session mailSession = Session.getInstance(props, authenticator);

            MimeMessage message = new MimeMessage(mailSession);

            String username = props.getProperty("mail.user");
            InternetAddress form = new InternetAddress(username);
            message.setFrom(form);

            InternetAddress toAddress = new InternetAddress(to);
            message.setRecipient(Message.RecipientType.TO, toAddress);

            message.setSubject(title);

            message.setContent(text, "text/html;charset=UTF-8");

            Transport.send(message);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


}
