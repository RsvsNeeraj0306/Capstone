package com.first.capstone.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.first.capstone.dto.ResponseDTO;
import com.first.capstone.entity.Software;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class GmailService {

    @Autowired
    private JavaMailSender sender;

    public ResponseEntity<ResponseDTO> sendMail() {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo("neerajrenkireddy01@gmail.com");
            helper.setText("Greetings :)! Test Run for Capstone NTM project!");
            helper.setSubject("Mail From Spring Boot");

        } catch (MessagingException e) {
            ResponseDTO responseDTO = new ResponseDTO();
            e.printStackTrace();
            responseDTO.setResponseBody("Error while sending mail ..");
            return ResponseEntity.ok(responseDTO);

        }
        sender.send(message);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseBody("Mail Sent Success!");
        return ResponseEntity.ok(responseDTO);
    }

     public void sendLicenseExpirationEmail(Software software) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
           helper.setTo("neerajrenkireddy01@gmail.com");
            helper.setSubject("License Expiration Notification");
            helper.setText("Your license for software " + software.getSoftwareName() + " has expired. Please renew the license.");
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle the exception
        }
        sender.send(message);
    }
}
