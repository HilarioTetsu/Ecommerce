package com.ecommerce.order.models.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.order.models.enums.Estado;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor              
@AllArgsConstructor            
@Builder                        
@ToString  
@EqualsAndHashCode(onlyExplicitlyIncluded = true) 
public class Order {
	
	@Id
	@Column(length = 36, nullable = false)
	@EqualsAndHashCode.Include
	private String orderId;
		
	@Column(nullable = false,precision = 10,scale = 2)
	private BigDecimal totalPrice;
	
	@OneToMany(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DetailOrder> details;
			
	@Column(name = "fecha_creacion",nullable = false)
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
	
	public void addDetailOrder(DetailOrder detail) {
		this.details.add(detail);
		detail.setOrder(this);		
	}

	
}
