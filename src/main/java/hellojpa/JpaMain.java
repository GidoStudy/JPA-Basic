package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        //code
        transaction.begin();
        try{
            //비영속 상태
            Member member = new Member();
            member.setId(1L);
            member.setName("Hello");
            // 영속 상태로 전환 -> 영속성 컨텍스트를 통해 관리된다.
            em.persist(member);
            Member findMember = em.find(Member.class, 1L);
            findMember.setName("HelloJPA");


            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }finally {
            em.close();
        }
        emf.close();



    }
}
