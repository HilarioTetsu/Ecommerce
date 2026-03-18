package com.ecommerce.product.models.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ProductResponseDto {
	
	
	@NotNull
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
	private SubCategoriaResponseDto subCategoria;
	
}
