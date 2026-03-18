package com.ecommerce.product.models.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ecommerce.product.models.enums.Estado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor              
@AllArgsConstructor            
@Builder                        
@ToString  
@EqualsAndHashCode(onlyExplicitlyIncluded = true) 
public class Product {

	@Id
	@Column(length = 36)
	@Size(min = 36, max = 36)
	@EqualsAndHashCode.Include
	private String id;
	
	@Column(nullable = false)	
	private String nombre;
	
	@Lob
	@Column(columnDefinition = "TEXT",name = "descripcion")	
	private String desc;
	
	@Column(nullable = false,precision = 10,scale = 2)
	private BigDecimal precio;
	
	@Column(nullable = false)
	private Integer stock;
	
	@Column(name = "fecha_creacion",nullable = false)
	private LocalDateTime fechaCreacion;


	@Column(name = "fecha_modificacion", nullable = true)
	private LocalDateTime fechaModificacion;

	@ManyToOne
	@JoinColumn(name = "subcategoria_id")
	private SubCategoria subCategoria;
	
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
