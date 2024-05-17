package hellojpa;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "member_seq_generator",
sequenceName = "member_seq")
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "member_seq_generator") // 자동지정 오라클이면 sequence MYSQL에는 Auto Increment
    // GenerationType.IDENTITY => 키 생성을 DB에 위임
    private Long id;

    @Column(name = "name") //DB의 컬럼명은 name
    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;



    public void setTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

    /*  private Integer age;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    private LocalDate createDate2;
    private LocalDateTime credateDate3;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Lob
    private String description;*/

}
