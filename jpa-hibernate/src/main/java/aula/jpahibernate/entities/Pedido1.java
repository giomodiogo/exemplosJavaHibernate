package aula.jpahibernate.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import aula.jpahibernate.enums.EPedidoStatus;

@Entity
@Table(name = "pedido_1_db")
public class Pedido1 extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne // Muitos pedidos para um cliente
	@JoinColumn(name = "cliente_id")
	private Cliente1 cliente;

	@Enumerated(EnumType.STRING)
	private EPedidoStatus status;

	@ManyToMany
	@JoinTable(name = "pedido_produto_1_db", joinColumns = { @JoinColumn(name = "pedido_id") }, inverseJoinColumns = {
			@JoinColumn(name = "produto_id") })
	private List<Produto1> produtos;

	public Pedido1() {

	}

	public Pedido1(Cliente1 cliente, EPedidoStatus status, List<Produto1> produtos) {
		this.cliente = cliente;
		this.status = status;
		this.produtos = produtos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente1 getCliente() {
		return cliente;
	}

	public void setCliente(Cliente1 cliente) {
		this.cliente = cliente;
	}

	public EPedidoStatus getStatus() {
		return status;
	}

	public void setStatus(EPedidoStatus status) {
		this.status = status;
	}

	public List<Produto1> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto1> produtos) {
		this.produtos = produtos;
	}
}
