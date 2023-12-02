package jwt.backend.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileDto {
    private String base64;
    private String name;
    private String type;
    private String lastModified;
}
