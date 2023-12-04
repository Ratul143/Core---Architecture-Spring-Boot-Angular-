package jwt.backend.controller.ping;

import jwt.backend.constant.ApiUrl;
import jwt.backend.dto.request.mail.MailRequest;
import jwt.backend.service.EmailService;
import jwt.backend.service.user_management.AuthService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.queue.PredicatedQueue;
import org.aspectj.bridge.IMessage;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(ApiUrl.BASE_API + "/ping")
public class PingController {
    private final AuthService authService;
    private final EmailService mailService;

    @GetMapping()
    public String getPing() {

        return authService.getAuthUser().getFullName();
    }

    @GetMapping("/send-mail")
    public String sendMail(){
        mailService.sendNewPasswordEmail("Forruq", "Forruq", "Forruq", "forruq.cho@dekkolegacy.com", "ADMIN" );
     return "Mail has been sent";
    }
}
