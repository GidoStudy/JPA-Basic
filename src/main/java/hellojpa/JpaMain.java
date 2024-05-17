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
           /* Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("Name");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();
            em.flush, clear를 해줘야 DB에서 다시 select 해서 깔끔하게 찾아진다
            만약 안하고 그냥 하게되면 1차캐시에서 가져와서 team의 getMember 해봤자 List의 size : 0
            연관 관계 다대일 설정 잘해줘야 한다
            Member findMember = em.find(Member.class, member.getId());
            System.out.println("findMember = " + findMember.getTeam().getMembers());
//            System.out.println("members.size() = " + members.size());
            Team findTeam = findMember.getTeam();
            System.out.println("findTeam = " + findTeam.getName());*/
            /* 상속관계
            Movie movie = new Movie();
            movie.setDirector("a");
            movie.setActor("bbb");
            movie.setName("바람과 함꼐 사라지다");
            movie.setPrice(10000);
            em.persist(movie);
            em.flush();
            em.clear();
            Movie findMovie = em.find(Movie.class, movie.getId());*/
            Member member = new Member();
            Member member2 = new Member();
            member.setUsername("hello");
            member2.setUsername("hello2");
            em.persist(member2);
            em.persist(member);
            em.flush();
            em.clear();
//            Member findMember = em.find(Member.class, member.getId());
            Member findMember = em.getReference(Member.class, member.getId());
            Member findMember2 = em.getReference(Member.class, member2.getId());
            // 프록시가 한번 초기화 되면 계속 프록시인 상태로 사용하기 때문에
            // 비교를 ==이 아닌 instance of로 비교해야된다 프록시 객체는 원본 엔티티를 상속받음
            System.out.println("findMember.getId() = " + findMember.getId());
            // 처음엔 프록시에서 getId를 바로 할 수 있어서 쿼리문 안나가고 실제 엔티티에 접근 해야할때
            // 쿼리문 나간뒤에 실제 엔티티의 값에서 getter로 받아온다
            System.out.println("findMember.getUsername() = " + findMember.getUsername());
            // 여기서 Proxy로 넘어올지 Entity로 넘어올지 모른다 그래서 == 비교말고 instacneof 로 써야함
            System.out.println("findMember == findmember2" + (findMember.getClass() == findMember2.getClass()));
            System.out.println("findMember == findmember2" + (findMember instanceof Member));
            System.out.println("findMember2 == findmember2" + (findMember2 instanceof Member));
            // -----------------------------
            em.flush();
            em.clear();
            //영속성 컨텍스트에 이미 member1이 등록 -> 프록시가 아니고 엔티티로 반환해준다
            Member member1 = em.find(Member.class, member.getId());
            Member reference = em.getReference(Member.class, member1.getId());
            System.out.println("reference = " + reference.getClass());
            // 영속성 컨텍스트에서 가져온 친구들이다 -> 같은 객체를 보장함
            System.out.println("a == a : " + (member1 == reference));


            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }

}
