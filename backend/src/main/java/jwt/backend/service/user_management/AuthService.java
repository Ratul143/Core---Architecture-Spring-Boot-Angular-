package jwt.backend.service.user_management;


import jwt.backend.dto.user_management.AccsUserListDto;
import jwt.backend.dto.user_management.Passwords;
import jwt.backend.entity.user_management.accs_auth.Accs_Auth_User;
import org.springframework.data.domain.Page;

public interface AuthService {
    Long getAuthUserId();

    Accs_Auth_User getAuthUser();

    Page<AccsUserListDto> authUserList(String searchValueForUsername,
                                       String searchValueForEmail,
                                       String searchValueForUserType,
                                       String searchValueForCellNo,
                                       Integer page, Integer size);

    Accs_Auth_User findUserByUsername(String username);

    Accs_Auth_User findUserById(Long id);

    Passwords encodePasswordSha256(String password);
}
