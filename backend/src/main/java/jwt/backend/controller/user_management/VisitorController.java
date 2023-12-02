package jwt.backend.controller.user_management;

import jwt.backend.constant.ApiUrl;
import jwt.backend.entity.user_management.RecentVisitors;
import jwt.backend.exception.ExceptionHandling;
import jwt.backend.service.CommonService;
import jwt.backend.service.user_management.AuthService;
import jwt.backend.service.user_management.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping(ApiUrl.BASE_API + ApiUrl.VISITOR)
public class VisitorController extends ExceptionHandling {

    private final UserService userService;
    private final CommonService commonService;
    private final AuthService authService;

    @PostMapping(ApiUrl.RECENT_VISITOR)
    public ResponseEntity<?> recentVisitor(HttpServletRequest request) {
        return new ResponseEntity<>(commonService.recentVisitors(request, authService.getAuthUser(), "HOME"), OK);
    }

    @GetMapping(ApiUrl.LIST)
    public ResponseEntity<?> list(@RequestParam("search") String search, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        page = page < 0 ? 0 : page - 1;
        size = size <= 0 ? 10 : size;
        List<RecentVisitors> userList = commonService.findVisitorsList(search, page, size);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping(ApiUrl.COUNT)
    public ResponseEntity<?> count() {
        return new ResponseEntity<>(commonService.visitorsCount(), HttpStatus.OK);
    }
}
