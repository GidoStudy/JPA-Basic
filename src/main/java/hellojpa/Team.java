package hellojpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Team extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long Id;
    private String name;
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    public void addMember(Member member) {
        member.setTeam(this);
        members.add(member);
    }
}
