package hellojpa;

import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
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
            /*
            //비영속 상태
            Member member = new Member();
            Member member2 = new Member();

            // 영속 상태로 전환 -> 영속성 컨텍스트를 통해 관리된다.
            em.persist(member);
            em.persist(member2);
            // 변경감지를 통해 update 쿼리문을 알아서 만들어준다
            member2.setUsername("HelloB");
            member.setUsername("HelloA");
            // 영속성 컨텍스트 1차 캐시에서 바로 가져오기 때문에 결과가 true 로 나온다
            Member member1 = em.find(Member.class, 1L);
            Member memberB = em.find(Member.class, 1L);
            System.out.println("res = " + (member1 == memberB));
            System.out.println("member1.getName() = " + member1.getUsername());
            System.out.println("member1.getId() = " + member1.getId());
            System.out.println("==============================="); // commit 할때 한번에 flush

            // JPQL : 단순한 조회가 아닌 뭐 조건, 조인 등이 들어갈때 (통계성 쿼리)
            // JPQL 쿼리 실행 시 flush()가 자동으로 호출된다.
            List<Member> selectMFromMemberM = em.createQuery("select m from Member m", Member.class)
                    .getResultList();
            for (Member mem : selectMFromMemberM) {
                System.out.println("mem = " + mem);
            }

             *//*


            //객체 지향이 아니고 DB에 맞춰 데이터 중심으로 설계했을때 불편함
            Team team = new Team();
            Member member = new Member();

            team.setName("TeamA");
            // 연관관계 편의 메소드를 생성
            // addMember 에 추가로 member 에 해당 팀을 추가해둠
            team.addMember(member);
            em.persist(team);

            member.setUsername("member1");
            //가장 많이 하는 실수 : 연관관계의 주인에 값을 입력하지 않는다 (역방향에만 연관관계 설정)
            //team.getMembers().add(member)는 하는데 member.setTeam(team)을 안해버린다


            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();
            *//*
            em.flush, clear를 해줘야 DB에서 다시 select 해서 깔끔하게 찾아진다
            만약 안하고 그냥 하게되면 1차캐시에서 가져와서 team의 getMember 해봤자 List의 size : 0
            List<Member> 컬렉션에 값을 안넣었기 때문에 값이 찾아지지 않는다. 양방향일땐 둘다 값 세팅해주자.
            연관 관계 다대일 설정 잘해줘야 한다
            *//*
            Member findMember = em.find(Member.class, member.getId());
            System.out.println("team1 = " + findMember.getTeam().getName());
            List<Member> members = findMember.getTeam().getMembers();
            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }
            --- 연관 관계 ----
            */


            /**
             * 양방햔 연관관계
             * 객체의 양방향 연관관계는 2개의 단방향 연관관계가 존재하는 것이다
             * Table 에서는 JOIN 을 통해 외래키 하나로 두 테이블의 연관관계를 관리하지만 객체는 그렇지 않음
             * 그래서 두개의 객체중 하나로 외래키를 괸리 해야하는데 이때 관계의 주인
             * -> 외래 키를 가지는 쪽을 연관관계의 주인으로 정하고, 주인이 아닌쪽에서 mappedBy 속성으로 주인 지정
             * 반대쪽으로 잡게되면 한쪽에 변경을 걸면 다른 테이블에 쿼리가 나감 -> 복잡해지면 이해하기가 힘들다
             *
             * 양방향 매핑시에 toString, JSON 라이브러리 등을 사용하면 서로 호출하며 무한 루프에 빠질 수 있다
             * -> toString 롬복 사용 X, Controller 에서 엔티티를 반환하지 말고 다 DTO 로 반환하는게 좋다
             *
             * 정리 : 양방향 매핑은 반대 방향으로 조회기능이 추가된 것 뿐 / JPQL에서 역방향으로 탐색할 일이 많다
             * 단방향으로 매핑을 마무리 하고 필요할 때 양방향 매핑을 추가하는 것이 좋다
             */

         /*   // 상속관계
            // 기본 전략은 SINGLE_TABLE
            Movie movie = new Movie();
            movie.setDirector("dirA");
            movie.setActor("actB");
            movie.setName("바람과 함께 사라지다");
            movie.setPrice(10000);
            // Item 테이블에 한번, Movie 테이블에 한 번 총 2번 insert 쿼리가 나감
            em.persist(movie);

            em.flush();
            em.clear();
            // JOINED 전략으로 했을때 조회 시 join 해서 조회해옴.
            Movie findMovie = em.find(Movie.class, 1L);

            // @MappedSuperCLass 를 통한 BaseEntity 를 상속받아 상속만 사용
            Member member = new Member();
            member.setUsername("PARK");
            member.setCreatedBy("KIM");
            member.setCreatedDate(LocalDateTime.now());
            em.persist(member);*/


           // 프록시
           /* Member member = new Member();
            Team team = new Team();
            team.setName("teamA");
            member.setUsername("Kim");
            em.persist(member);
            em.persist(team);
            member.setTeam(team);

            em.flush();
            em.clear();

            Member findMember = em.getReference(Member.class, 1L);
            System.out.println("findMember.getClass() = " + findMember.getClass());
            System.out.println("findMember.getId() = " + findMember.getId());
            // getReference 를 하면 Proxy 객체로 만들어서 던져주고 실제 필요할 때 쿼리를 날려 가져온다
            System.out.println("findMember.getUsername() = " + findMember.getUsername());
            System.out.println("findMember.getTeam().getName() = " + findMember.getTeam().getName());
            // 프록시가 한번 초기화 되면 계속 프록시인 상태로 사용하기 때문에
            // 비교를 ==이 아닌 instance of로 비교해야된다 프록시 객체는 원본 엔티티를 상속받음
            System.out.println("(findMember == member) = " + (findMember.getClass() == member.getClass()));
            System.out.println("(findMember instanceof Member) = " + (findMember instanceof Member));
            // 처음엔 프록시에서 getId를 바로 할 수 있어서 쿼리문 안나가고 실제 엔티티에 접근 해야할때
            // 쿼리문 나간뒤에 실제 엔티티의 값에서 getter로 받아온다
            // -----------------------------

            em.flush();
            em.clear();

            Member member1 = em.find(Member.class, member.getId());
            //영속성 컨텍스트에 이미 member1이 등록 -> 프록시가 아니고 엔티티로 반환해준다
            //만약 em.getReference로 가져와서 프록시였으면 뒤에도 프록시로 가져옴
            Member reference = em.getReference(Member.class, member.getId());
            // 영속성 컨텍스트에서 가져온 친구들이다 -> 같은 객체를 보장함
            System.out.println("reference = " + reference.getClass());*/

            // 지연 로딩
            /* Member member = new Member();
            Team team = new Team();
            team.setName("teamA");
            member.setUsername("KIM");
            member.setTeam(team);

            em.persist(member);
            em.persist(team);

            em.flush();
            em.clear();
            // 지연 로딩으로 member만 찾아 올때는 Team은 프록시로 가져옴
            // EAGER 로하면 여기서 바로 select 해서 엔티티로 가져옴
            // 가급적 지연로딩만 사용 / 즉시 로딩은 예쌍치 못한 SQL이 발생
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.getClass() = " + findMember.getClass());
            System.out.println("findMember.getTeam().getClass() = " + findMember.getTeam().getClass());
            System.out.println(" ==================== ");
            findMember.getTeam().getMembers(); // 초기화  */

            // 영속성 CASCADE -> 연관 관계 매핑등과는 전혀 관계 없음
            // 단일 엔티티에 완전히 종속적이면서 라이프 사이클이 유사할때 사용
            // parent 를 persist 할때 child도 persist가 되었으면 좋겠다 -> CASCADE
            /*Parent parent = new Parent();
            Child child1 = new Child();
            Child child2 = new Child();

            parent.addChild(child1);
            parent.addChild(child2);
            em.persist(parent);*/


            // 고아객체
            // 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제
            // 부모 쪽에서의 컬렉션에서 빠졌는데 child 에 delete 쿼리가 나감


            // 임베디드 타입을 쓰더라도 테이블 자체는 같음
            // 객체와 테이블을 세밀하게 설정하는 것이 가능 -> 클래스도 컬럼하나로 야무지게 매핑 해줌
           /* Address address = new Address("busan", "street", "zipcode");

            Member member1 = new Member();
            member1.setUsername("hello");
            member1.setAddress(address);
            em.persist(member1);

            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());
            Member member2 = new Member();
            member2.setUsername("hello");
            member2.setAddress(copyAddress);
            em.persist(member2);

            member1.getAddress().setCity("newCity");*/
            // 애초에 다른 객체로 바꿔서 써야함 setter를 없애고 생성자로 불변 객체로 만들어버리면 된다
            // 이렇게 되어 있으면 member랑 member2 둘다 newCity로 바뀐다
            // 값 타입의 실제 인스턴스를 공유하는 것은 위험 하기때문에 값을 복사해서 사용

            /*Member member = new Member();
            member.setUsername("user");
            member.setAddress(new Address("city1", "street", "10032"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("족발");

            member.getAddressHistory().add(new Address("city1", "street", "10032"));
            member.getAddressHistory().add(new Address("city2", "street2", "130032"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("=========== START ============");
            Member findMember = em.find(Member.class, member.getId());

            // 값 타입 수정
            //findMember.getAddress().setCity("newCity");
            // 값 타입을 수정하고 싶을땐 아래와 같이 완전히 새로 세팅해야 한다
            // 위처럼 setter로 변경하면 side effect 가 발생할 가능성 있음
            Address address = findMember.getAddress();
            findMember.setAddress(new Address("newCity", address.getStreet(), address.getZipcode()));

            // 치킨 -> 한식
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            // 삭제할때 equals Override 잘 해놔야 삭제가 정상적으로 진행된다
            findMember.getAddressHistory().remove(new Address("city1", "street", "10032"));
            findMember.getAddressHistory().add(new Address("NewCity1", "street", "10032"));
            */


            /* 값 타입 조회
            List<Address> addressHistory = findMember.getAddressHistory();
            for (Address address : addressHistory) {
                System.out.println("address.getCity() = " + address.getCity());
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String favoriteFood : favoriteFoods) {
                System.out.println("favoriteFood = " + favoriteFood);
            }*/

            List<Member> resultList = em.createQuery("select m from Member m where m.username like '%kim%'", Member.class)
                    .getResultList();
            for (Member member : resultList) {
                System.out.println("member = " + member);
            }



            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }




}
