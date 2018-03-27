package aula.jpahibernate;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import aula.jpahibernate.entities.Cliente1;
import aula.jpahibernate.entities.EntityBase;
import aula.jpahibernate.entities.Pedido1;
import aula.jpahibernate.entities.Produto1;
import aula.jpahibernate.enums.EPedidoStatus;

public class JpaTest {
	private final long IdPedido = 1L;
	private EntityManager em;

	public static void main(String[] args) {
		new JpaTest();
	}

	public JpaTest() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("AulaPU");
		em = emf.createEntityManager();

		// Inclusão de dados.
		create();

		// Edição do status do pedido
		updateStatusPedido();

		// Listar
		list();
		// Remover
		remove();
	}

	private void create() {
		Cliente1 cliente = createCliente();
		List<Produto1> produtos = createProdutos();
		createPedido(cliente, produtos);
	}

	private void updateStatusPedido() {
		Pedido1 pedido = em.find(Pedido1.class, IdPedido);
		pedido.setStatus(EPedidoStatus.APROVADO);
		update(pedido);

	}

	private void list() {
		// Maneira 1
		// Pedido pedido = em.find(Pedido.class, IdPedido);

		// Maneira 2 HQL - Listar todos
		Query query = em.createQuery("SELECT pedido FROM Pedido1 pedido WHERE pedido.id = :idPedido");
		query.setParameter("idPedido", IdPedido);
		Pedido1 pedido = (Pedido1) query.getSingleResult();

		System.out.println("Pedido: " + pedido.getStatus().toString());
		pedido.getProdutos().forEach(produto -> {
			System.out.println("Produto: " + produto.getNome());
		});
	}

	private void remove() {
		Pedido1 pedido = em.find(Pedido1.class, IdPedido);
		remove(pedido);
		pedido.getProdutos().forEach(produto -> {
			remove(produto);
		});
	}

	private void createPedido(Cliente1 cliente, List<Produto1> produtos) {
		Pedido1 pedido = new Pedido1(cliente, EPedidoStatus.PENDENTE, produtos);
		persist(pedido);
	}

	private Cliente1 createCliente() {
		Cliente1 cliente = new Cliente1("Diogo", true);
		persist(cliente);
		return cliente;

	}

	private List<Produto1> createProdutos() {
		List<Produto1> produtos = new LinkedList<Produto1>();
		for (int i = 0; i < 5; i++) {
			Produto1 prod = new Produto1("Produto " + i + " - " + new Date().getTime());
			persist(prod);
			produtos.add(prod);
		}
		return produtos;
	}

	private void persist(EntityBase entity) {
		try {
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}

	}

	private void update(EntityBase entity) {
		try {
			em.getTransaction().begin();
			em.merge(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}

	private void remove(EntityBase entity) {
		try {
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}
}
