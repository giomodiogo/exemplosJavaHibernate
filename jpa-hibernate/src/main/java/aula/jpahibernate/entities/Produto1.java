package aula.jpahibernate.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "produto_1_db")
public class Produto1 extends EntityBase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	public Produto1() {

	}

	public Produto1(String nome) {
		this.nome = nome;
	}

	 @ManyToMany(mappedBy = "produtos")
	// @ManyToMany
	// @JoinTable(name = "pedido_produto1_db", joinColumns = { @JoinColumn(name =
	// "produto_id") }, inverseJoinColumns = {
	// @JoinColumn(name = "pedido_id") })
	private List<Pedido1> pedidos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Pedido1> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido1> pedidos) {
		this.pedidos = pedidos;
	}

}
