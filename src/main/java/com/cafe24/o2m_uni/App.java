package com.cafe24.o2m_uni;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.cafe24.o2m_uni.domain.Member;
import com.cafe24.o2m_uni.domain.Team;

public class App {
	public static void main(String[] args) {
		// 1. Entity Manager Factory 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("o2m-uni"); // db

		// 2. Entity Manager 생성
		EntityManager em = emf.createEntityManager();

		// 3.Get TX
		EntityTransaction tx = em.getTransaction();

		// 4. TX Begins
		tx.begin();

		// 5. Business Logic
		try {
			Member member1 = new Member();
			member1.setName("회원1");

			Member member2 = new Member();
			member2.setName("회원2");

			Team team1 = new Team();
			team1.setName("team1");
			team1.getMembers().add(member1);
			team1.getMembers().add(member2);

			em.persist(member1); // insert member1
			em.persist(member2); // insert member2
			em.persist(team1);   // insert team1
								 // update member1's FK
								 // update member2's FK

			// OneToMany도 가능은 하지만, 불필요한 update 쿼리문이 실행된다. => ManyToOne이 더 좋다.
			
			// OneToMany만으로는 양방향 관계를 쓸 수 없다.

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}

		// 6. TX Commit
		tx.commit();

		// 7. Entity Manager 종료
		em.clear();

		// 8. Entity Manager Factory 종료
		emf.close();
	}

}
