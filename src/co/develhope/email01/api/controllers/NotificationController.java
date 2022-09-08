package co.develhope.email01.api.controllers;

import co.develhope.email01.emailService.EmailService;
import co.develhope.email01.api.entities.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import co.develhope.email01.students.entities.Student;
import co.develhope.email01.students.services.StudentService;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class NotificationController {

    @Autowired
    StudentService studentService;

    @Autowired
    EmailService emailService;

    @PostMapping("/notification")
    public ResponseEntity sendNotification(@RequestBody NotificationDTO payload){
        try{
            Student studentToNotify = studentService.getStudentById(payload.getContactId());
            System.out.println("studentToNotify: " + studentToNotify);
            if (studentToNotify == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student not found");
            emailService.sendTo(studentToNotify.getEmail(), payload.getTitle(), payload.getText());
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Broken server");
        }
    }
}