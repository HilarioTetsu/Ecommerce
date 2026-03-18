package com.ecommerce.product.models.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.product.models.enums.Estado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "subcategoria")
@Getter
@Setter
@NoArgsConstructor              
@AllArgsConstructor            
@Builder                        
@ToString  
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SubCategoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(nullable = false,unique = true)
	private String nombre;
	
	@JoinColumn(name = "categoria_id",nullable = false)
	@ManyToOne
	private Categoria categoria;
	
	@Column(name = "fecha_creacion",nullable = false)	
	private LocalDateTime fechaCreacion;


	@Column(name = "fecha_modificacion", nullable = true)
	private LocalDateTime fechaModificacion;

	@OneToMany(mappedBy = "subCategoria")
	List<Product> productos;
	
	@Column(nullable = false)
	private Estado status;

	
	@PrePersist
	public void prePersist() {
		this.fechaCreacion = LocalDateTime.now();		
		this.status = Estado.ACTIVO;
	}

	@PreUpdate
	public void preUpdate() {		
		this.fechaModificacion = LocalDateTime.now();
	}

	
}
