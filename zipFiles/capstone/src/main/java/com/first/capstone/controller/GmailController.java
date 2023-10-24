package com.first.capstone.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.first.capstone.dto.ResponseDTO;
import com.first.capstone.services.GmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@CrossOrigin()
@RestController
@RequestMapping("/api")
public class GmailController {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private GmailService gmailService;

    @RequestMapping("/sendMail")
    public ResponseEntity<ResponseDTO> sendMail() {
        return gmailService.sendMail();
    }

       


    @RequestMapping("/sendMailAtt")

    public String sendMailAttachment() throws MessagingException {

        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        try {

            helper.setTo("demo@gmail.com");

            helper.setText("Greetings :)\n Please find the attached docuemnt for your reference.");

            helper.setSubject("Mail From Spring Boot");

            ClassPathResource file = new ClassPathResource("document.PNG");

            helper.addAttachment("document.PNG", file);

        } catch (MessagingException e) {

            e.printStackTrace();

            return "Error while sending mail ..";

        }

        sender.send(message);

        return "Mail Sent Success!";

    }

}