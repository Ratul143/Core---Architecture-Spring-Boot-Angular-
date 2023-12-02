package jwt.backend.entity.user_management;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;

/**
 * @author Jaber
 * @date 7/24/2022
 * @time 12:48 PM
 */
@Entity
@Getter
@Setter
@Immutable
@Table(name = "HR_EMPLOYEE_VIEW")
@Subselect("select * from HR_EMPLOYEE_VIEW")
public class HREmployeeView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    private String autoFormattedId;
    private String workMail;
    private String fullName;
    private String workPhone;
}
