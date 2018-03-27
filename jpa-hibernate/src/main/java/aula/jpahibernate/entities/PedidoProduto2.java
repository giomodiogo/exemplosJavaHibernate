package aula.jpahibernate.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pedido_produto_2_db")
public class PedidoProduto2 extends EntityBase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public PedidoProduto2() {

	}

	public PedidoProduto2(Pedido2 pedido2, Produto2 produto2) {
		this.pedido2 = pedido2;
		this.produto2 = produto2;
	}

	@ManyToOne(targetEntity = Pedido2.class) // Muitos pedido produto para um pedido
	@JoinColumn(name = "pedido_id")
	private Pedido2 pedido2;

	@ManyToOne // Muitos pedido produto para um produto
	@JoinColumn(name = "produto_id")
	private Produto2 produto2;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pedido2 getPedido2() {
		return pedido2;
	}

	public void setPedido2(Pedido2 pedido2) {
		this.pedido2 = pedido2;
	}

	public Produto2 getProduto2() {
		return produto2;
	}

	public void setProduto2(Produto2 produto2) {
		this.produto2 = produto2;
	}

}
