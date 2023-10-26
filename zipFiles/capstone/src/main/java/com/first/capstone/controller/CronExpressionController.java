package com.first.capstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.first.capstone.dto.CronExpressionDTO;
import com.first.capstone.dto.ResponseDTO;
// import com.first.capstone.services.GmailNotificationService;

@RestController
@RequestMapping("/api/cron")
public class CronExpressionController {

    // @Autowired
    // private GmailNotificationService gmailNotificationService;

    // @GetMapping("/getCron")
    // public ResponseEntity<ResponseDTO> getCronExpression() {
    //     return gmailNotificationService.getCronExpression();
    // }

    // @PutMapping("/updateCron")
    // public ResponseEntity<ResponseDTO> updateCustomCronExpression(@RequestBody CronExpressionDTO cronExpressionDTO) {
    //     gmailNotificationService.setCronExpression(cronExpressionDTO);
    //     ResponseDTO responseDTO = new ResponseDTO();
    //     responseDTO.setResponseBody("Cron expression updated successfully");
    //     return ResponseEntity.ok().body(responseDTO);
    // }
}
