package com.first.capstone.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.first.capstone.dto.CronExpressionDTO;
import com.first.capstone.dto.ResponseDTO;
import com.first.capstone.entity.Software;
import com.first.capstone.respositories.SoftwareRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GmailNotificationService {

    @Autowired
    private GmailService gmailService;

    @Autowired
    private SoftwareRepository softwareRepository;

    private String customCronExpression = "0 0 0 * * ?"; // Default cron expression for midnight

    public GmailNotificationService(
            SoftwareRepository softwareRepository,
            GmailService gmailService) {
        this.softwareRepository = softwareRepository;
        this.gmailService = gmailService;
    }

    public void setCustomCronExpression(String customCronExpression) {
        this.customCronExpression = customCronExpression;
    }

     private boolean isLicenseLessThanZeroDays(Software license) {
        LocalDate today = LocalDate.now();
        LocalDate expiryDate = license.getExpiryDate().toLocalDate();
        long daysDifference = ChronoUnit.DAYS.between(today, expiryDate);
        return daysDifference < 0;
    }

    private void sendLicenseExpirationEmail(Software license) {
        gmailService.sendLicenseExpirationEmail(license);
    }

    public void setCronExpression(@RequestBody CronExpressionDTO cronExpressionDTO) {
        String seconds = cronExpressionDTO.getSeconds();
        String minutes = cronExpressionDTO.getMinutes();
        String hours = cronExpressionDTO.getHours();
        String daysOfMonth = cronExpressionDTO.getDaysOfMonth();
        String months = cronExpressionDTO.getMonths();
        String daysOfWeek = cronExpressionDTO.getDaysOfWeek();

        setupdateCronExpression(seconds, minutes, hours, daysOfMonth, months, daysOfWeek);
    }

    @Scheduled(cron = "${gmailnotification.cron:0 0 0 * * ?}") // Use a placeholder for the cron expression
    public void scheduleLicenseExpirationNotifications() {
        checkAndSendLicenseExpirationNotifications();
    }

    public ResponseEntity<ResponseDTO> checkAndSendLicenseExpirationNotifications() {
        List<Software> licenses = softwareRepository.findAll();
        for (Software license : licenses) {
            if (isLicenseLessThanZeroDays(license)) {
                sendLicenseExpirationEmail(license);
            }
        }
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseBody("License expiration notifications sent successfully");
        return ResponseEntity.ok().body(responseDTO);
    }

    public ResponseEntity<ResponseDTO> getCronExpression() {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseBody(customCronExpression);
        return ResponseEntity.ok().body(responseDTO);
    }

    public ResponseEntity<ResponseDTO> setupdateCronExpression(String seconds, String minutes, String hours,
            String daysOfMonth, String months, String daysOfWeek) {
        StringBuilder cronExpressionBuilder = new StringBuilder();
        cronExpressionBuilder.append(seconds).append(" ");
        cronExpressionBuilder.append(minutes).append(" ");
        cronExpressionBuilder.append(hours).append(" ");
        cronExpressionBuilder.append(daysOfMonth).append(" ");
        cronExpressionBuilder.append(months).append(" ");
        cronExpressionBuilder.append(daysOfWeek);
        setCustomCronExpression(cronExpressionBuilder.toString());
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseBody("Custom cron expression updated.");
        return ResponseEntity.ok(responseDTO);
    }

   
}
