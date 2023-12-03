package jwt.backend.controller.ping;

import jwt.backend.constant.ApiUrl;
import jwt.backend.service.user_management.AuthService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.queue.PredicatedQueue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(ApiUrl.BASE_API + "/ping")
public class PingController {
    private final AuthService authService;

    @GetMapping()
    public String getPing() {

        return authService.getAuthUser().getFullName();
    }
}
