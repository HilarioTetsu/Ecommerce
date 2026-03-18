package com.ecommerce.product.models.entity;



import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.product.models.enums.Estado;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "categoria")
@Getter
@Setter
@NoArgsConstructor              
@AllArgsConstructor            
@Builder                        
@ToString  
@EqualsAndHashCode(onlyExplicitlyIncluded = true) 
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(nullable = false,unique = true)
	private String nombre;
	
	@OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SubCategoria> subCategorias;
	
	@Column(name = "fecha_creacion")
	@NotNull
	private LocalDateTime fechaCreacion;


	@Column(name = "fecha_modificacion", nullable = true)
	private LocalDateTime fechaModificacion;

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
	
	public void addSubCategoria(SubCategoria subCategoria) {
	    this.subCategorias.add(subCategoria);
	    subCategoria.setCategoria(this);
	}

	
}
