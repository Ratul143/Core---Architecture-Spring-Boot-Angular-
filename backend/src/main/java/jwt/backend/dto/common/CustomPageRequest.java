package jwt.backend.dto.common;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomPageRequest {

    private int pageNo;
    private int size;
    private String status;
    private String keyword;
}
