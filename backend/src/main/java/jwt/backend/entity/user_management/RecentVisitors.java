package jwt.backend.entity.user_management;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.Date;

/**
 * @author Jaber
 * @date 7/24/2022
 * @time 12:48 PM
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RECENT_VISITORS")
@SequenceGenerator(name = "recent_visitors_seq", sequenceName = "RECENT_VISITORS_SEQ", allocationSize = 1)
public class RecentVisitors {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recent_visitors_seq")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name= "IP_ADDRESS")
    private String ipAddress;

    @Column(name= "DEVICE")
    private String device;

    @Column(name= "USERNAME")
    private String username;

    @Column(name= "VISITOR_NAME")
    private String visitorName;

    @Column(name= "VISITOR_ROLE")
    private String visitorRole;

    @Column(name= "VISITOR_EMAIL")
    private String visitorEmail;

    @Column(name= "VISIT_TYPE")
    private String visitType;

    @Column(name= "VISITED_AT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date visitedAt;
}
