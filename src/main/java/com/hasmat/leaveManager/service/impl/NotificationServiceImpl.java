package com.hasmat.leaveManager.service.impl;

import com.hasmat.leaveManager.model.Leave;
import com.hasmat.leaveManager.model.User;
import com.hasmat.leaveManager.repository.UserRepository;
import com.hasmat.leaveManager.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void notifyLeaveApproval(Leave leave) {
        String empId = leave.getEmpId();

        Optional<User> optionalUser = userRepository.findByEmpId(empId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String recipientEmail = user.getUserEmail();

            // Create a simple email message
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipientEmail);
            message.setSubject("Leave Approval Notification");
            message.setText("Dear Employee " + user.getEmpId() + " ,\n\nYour leave request has been approved. Enjoy your time off!");

            // Send the email
            try {
                emailSender.send(message);
                log.info("Leave request approval mail sent successfully to: " + recipientEmail);
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error("Error occurred while sending leave request approval mail to: " + recipientEmail);
            }
        } else {
            log.error("Employee does not exist for empId: " +leave.getEmpId());
        }
    }

    @Override
    public void notifyLeaveRejection(Leave leave) {
        String empId = leave.getEmpId();

        Optional<User> optionalUser = userRepository.findByEmpId(empId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String recipientEmail = user.getUserEmail();

            // Create a simple email message
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipientEmail);
            message.setSubject("Leave Rejection Notification");
            message.setText("Dear Employee " + user.getEmpId() + " ,\n\nYour leave request has been rejected. Please contact your manager for more details.");

            // Send the email
            try {
                emailSender.send(message);
                log.info("Leave request rejection mail sent successfully to: " + recipientEmail);
            }catch(Exception ex){
                ex.printStackTrace();
                log.error("Error occurred while sending leave request rejection mail to: " + recipientEmail);
            }
            } else {
            log.error("Employee does not exist for empId: " +leave.getEmpId());
        }
    }
}
