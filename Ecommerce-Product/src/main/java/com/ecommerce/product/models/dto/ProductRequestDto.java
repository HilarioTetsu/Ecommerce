package com.ecommerce.product.models.dto;


import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

	private String id;
	
	@NotBlank
	private String nombre;
	@NotBlank
	private String desc;
	@NotNull
	private BigDecimal precio;
	@NotNull
	private Integer stock;
	@NotNull
	private SubCategoriaRequestDto subCategoria;
	
}
