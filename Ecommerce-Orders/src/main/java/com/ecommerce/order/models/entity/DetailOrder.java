package com.ecommerce.order.models.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ecommerce.order.models.enums.Estado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "detail_orders")
@Getter
@Setter
@NoArgsConstructor              
@AllArgsConstructor            
@Builder                        
@ToString  
@EqualsAndHashCode(onlyExplicitlyIncluded = true) 
public class DetailOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;
	
	@Column(name = "product_id", nullable = false, length = 36)
	private String productId;
	
	@Column(nullable = false,precision = 10,scale = 2)
	private BigDecimal unitPrice;
	
	@Column(nullable = false)
	private Integer quantity;
			
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


}
