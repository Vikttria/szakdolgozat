package hu.vikttria.zalog_program.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final Log log = LogFactory.getLog(this.getClass());

    private JavaMailSender javaMailSender;
    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${spring.mail.username}")
    private String KULDO;


    public void uzenetKuldesUgyfel(String email, String nev, String jelszo) {
        SimpleMailMessage message = null;

        try {
            message = new SimpleMailMessage();
            message.setFrom(KULDO);
            message.setTo(email);
            message.setSubject("Üdv Cégünknél");
            message.setText("Kedves " + nev + "!\n\nKöszönjük, hogy Nálunk zálogosított el! Egy mobil webalkalmazáson keresztül nyomon követheti zálogjegyét!\n\nA bejelentkezéshez szükséges e-mail cím: " + email + "\nJelszó: " + jelszo);

            javaMailSender.send(message);

            log.info("Sikeres e-mail küldés");
        }catch (Exception e){
            log.error("Hiba az email küldésben! " + e);
        }
    }

}
