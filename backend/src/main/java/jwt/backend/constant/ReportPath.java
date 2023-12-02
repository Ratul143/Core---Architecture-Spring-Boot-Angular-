package jwt.backend.constant;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ReportPath {
    public String basePath = AppConstant.REPORT_DIRECTORY;
}
