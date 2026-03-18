package com.ecommerce.product.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoriaRequestDto {
	
	private Long id;
	
	@NotBlank
	private String nombre;
	
}
