package hellojpa;

import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.logging.Logger;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        //code
        transaction.begin();
        try{
            /*
            //비영속 상태
            Member member = new Member();
            member.setId(1L);
            member.setName("Hello");
            Member member2 = new Member(2L,"B");
            // 영속 상태로 전환 -> 영속성 컨텍스트를 통해 관리된다.
            em.persist(member);
            em.persist(member2);
            Member findMember = em.find(Member.class, 1L);
            findMember.setName("HelloJPA"); // 변경 감지를 통해 update 쿼리가 나감
            System.out.println("==============================="); // commit 할때 한번에 flush
            */
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("Name");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());
            System.out.println("findMember = " + findMember.getTeam().getMembers());
//            System.out.println("members.size() = " + members.size());
            Team findTeam = findMember.getTeam();
            System.out.println("findTeam = " + findTeam.getName());
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }finally {
            em.close();
        }
        emf.close();



    }
}
