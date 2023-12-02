package jwt.backend.dto.user_management;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jwt.backend.dto.FileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Jaber
 * @Date 8/26/2022
 * @Time 11:12 AM
 * @Project backend
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String currentUsername;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String role;
    private boolean isActive;
    private boolean isNonLocked;
    private FileDto profileImage;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt = new Date();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt = new Date();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deletedAt = new Date();
    private Integer createdBy;
    private Integer updatedBy;
    private Integer deletedBy;
}
