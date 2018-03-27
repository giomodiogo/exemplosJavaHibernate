package aula.jpahibernate.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import aula.jpahibernate.enums.EPedidoStatus;

@Entity
@Table(name = "pedido_2_db")
public class Pedido2 extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(targetEntity = Cliente2.class) // Muitos pedidos para um cliente
	@JoinColumn(name = "cliente_id")
	private Cliente2 cliente;

	@Enumerated(EnumType.STRING)
	private EPedidoStatus status;

	public Pedido2() {

	}

	public Pedido2(Cliente2 cliente, EPedidoStatus status) {
		this.cliente = cliente;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente2 getCliente() {
		return cliente;
	}

	public void setCliente(Cliente2 cliente) {
		this.cliente = cliente;
	}

	public EPedidoStatus getStatus() {
		return status;
	}

	public void setStatus(EPedidoStatus status) {
		this.status = status;
	}
}
