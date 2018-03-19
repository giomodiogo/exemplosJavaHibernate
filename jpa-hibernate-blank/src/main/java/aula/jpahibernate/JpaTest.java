package aula.jpahibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaTest {
	private EntityManager em;

	public static void main(String[] args) {
		new JpaTest();
	}

	public JpaTest() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("AulaPU");
		em = emf.createEntityManager();
	}
}
