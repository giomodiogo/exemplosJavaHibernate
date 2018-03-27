package aula.jpahibernate;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Query;
import org.hibernate.Session;

import aula.jpahibernate.entities.Cliente2;
import aula.jpahibernate.entities.EntityBase;
import aula.jpahibernate.entities.Pedido2;
import aula.jpahibernate.entities.PedidoProduto2;
import aula.jpahibernate.entities.Produto2;
import aula.jpahibernate.enums.EPedidoStatus;

public class SessionTest2 {
	private final long IdPedido = 1L;
	private Session session;

	public static void main(String[] args) {

		new SessionTest2();
	}

	public SessionTest2() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("AulaPU");
		EntityManager em = emf.createEntityManager();
		session = em.unwrap(Session.class);

		// Inclusao de dados.
		create();

		// Edicao status pedido
		updateStatusPedido();

		// Listar
		list();

		// Remover
		remove();

	}

	private void create() {
		try {
			session.getTransaction().begin();
			Cliente2 cliente = createCliente();

			session.persist(cliente);
			Pedido2 pedido = createPedido(cliente);
			session.persist(pedido);
			List<Produto2> produtos = createProdutos();
			produtos.forEach(produto -> {
				session.persist(produto);

				PedidoProduto2 pedidoProduto = new PedidoProduto2(pedido, produto);
				session.persist(pedidoProduto);
			});
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		}
	}

	private void updateStatusPedido() {
		Pedido2 pedido = (Pedido2) session.load(Pedido2.class, IdPedido);
		pedido.setStatus(EPedidoStatus.APROVADO);
		update(pedido);

	}

	private void remove() {
		try {
			Pedido2 pedido = (Pedido2) session.load(Pedido2.class, IdPedido);
			Query query = session.createQuery("SELECT pp2 FROM PedidoProduto2 pp2 WHERE pp2.pedido2.id = :idPedido");
			query.setParameter("idPedido", IdPedido);
			List<PedidoProduto2> list = query.list();
			session.getTransaction().begin();
			list.forEach(ppd -> {
				session.delete(ppd);
			});
			session.delete(pedido);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		}
	}

	private void list() {
		Pedido2 pedido = (Pedido2) session.load(Pedido2.class, IdPedido);

		// Maneira 2 HQL - Listar alguns produtos
		Query query = session.createQuery(
				"SELECT pp2 FROM PedidoProduto2 pp2 WHERE pp2.pedido2.id = :idPedido AND pp2.produto2.id > :idProduto ");
		query.setParameter("idPedido", IdPedido);
		query.setParameter("idProduto", 3L);
		List<PedidoProduto2> list = query.list();
		System.out.println("Pedido: " + pedido.getStatus().toString());
		list.forEach(item -> {
			System.out.println("Pedido: " + item.getPedido2().getId() + " - Produto: " + item.getProduto2().getNome());
		});
	}

	private Pedido2 createPedido(Cliente2 cliente) {
		Pedido2 pedido = new Pedido2(cliente, EPedidoStatus.PENDENTE);
		return pedido;
	}

	private Cliente2 createCliente() {
		Cliente2 cliente = new Cliente2("Diogo", true);
		return cliente;

	}

	private List<Produto2> createProdutos() {
		List<Produto2> produtos = new LinkedList<Produto2>();
		for (int i = 0; i < 5; i++) {
			Produto2 prod = new Produto2("Produto " + i + " - " + new Date().getTime());
			produtos.add(prod);
		}
		return produtos;
	}

	// private void persist(EntityBase entity) {
	// session.getTransaction().begin();
	// session.persist(entity);
	// session.getTransaction().commit();
	// }

	private void update(EntityBase entity) {
		try {
			session.getTransaction().begin();
			session.merge(entity);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		}
	}
}
