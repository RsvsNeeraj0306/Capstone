package com.first.capstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.first.capstone.dto.ResponseDTO;
import com.first.capstone.services.GmailService;

@CrossOrigin()
@RestController
@RequestMapping("/api")
public class GmailController {

    @Autowired
    private GmailService gmailService;

    @GetMapping("/test")
    public String test() {
        gmailService.sendMail();
        return "test";
    }

    @RequestMapping("/sendMail")
    public ResponseEntity<ResponseDTO> sendMail() {
        return gmailService.sendMail();
    }


}