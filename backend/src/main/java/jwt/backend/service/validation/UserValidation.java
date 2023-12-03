package jwt.backend.service.validation;

import jwt.backend.dto.common.ValidationDto;
import jwt.backend.dto.request.auth.RegistrationRequest;
import jwt.backend.dto.request.auth.UserCreateRequest;
import jwt.backend.dto.request.auth.UserUpdateRequest;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserValidation {
    public ValidationDto validateCreateRequest(UserCreateRequest request) {
        if (request.getEmail() == null) {
            return new ValidationDto(false, "Email");
        } else if (request.getUsername() == null) {
            return new ValidationDto(false, "Username");
        } else if (request.getFullName() == null) {
            return new ValidationDto(false, "Full name");
        } else if (request.getUserTypeId() == 0) {
            return new ValidationDto(false, "User type id");
        } else if (request.getRoleId() == 0) {
            return new ValidationDto(false, "Role id");
        } else if (request.getEmployeeId() == null) {
            return new ValidationDto(false, "Employee id");
        } else if (request.getItemCategoryId() == null) {
            return new ValidationDto(false, "Item category id");
        }
        return new ValidationDto(true, "Valid");
    }

    public ValidationDto validateUpdateRequest(UserUpdateRequest request) {
        if (request.getId() == null) {
            return new ValidationDto(false, "Id");
        } else if (request.getEmail() == null) {
            return new ValidationDto(false, "Email");
        } else if (request.getUsername() == null) {
            return new ValidationDto(false, "Username");
        } else if (request.getFullName() == null) {
            return new ValidationDto(false, "Full name");
        } else if (request.getUserTypeId() == 0) {
            return new ValidationDto(false, "User type id");
        } else if (request.getRoleId() == 0) {
            return new ValidationDto(false, "Role id");
        } else if (request.getEmployeeId() == null) {
            return new ValidationDto(false, "Employee id");
        } else if (request.getItemCategoryId() == null) {
            return new ValidationDto(false, "Item category id");
        }
        return new ValidationDto(true, "Valid");
    }

    public ValidationDto validateRegistrationRequest(RegistrationRequest request) {
        if (request.getEmail() == null) {
            return new ValidationDto(false, "Email");
        } else if (request.getUsername() == null) {
            return new ValidationDto(false, "Username");
        } else if (request.getFullName() == null) {
            return new ValidationDto(false, "Full name");
        } else if (request.getUserTypeId() == 0) {
            return new ValidationDto(false, "User type id");
        } else if (request.getRoleId() == 0) {
            return new ValidationDto(false, "Role id");
        } else if (request.getEmployeeId() == null) {
            return new ValidationDto(false, "Employee id");
        } else if (request.getItemCategoryId() == null) {
            return new ValidationDto(false, "Item category id");
        }
        return new ValidationDto(true, "Valid");
    }
}
