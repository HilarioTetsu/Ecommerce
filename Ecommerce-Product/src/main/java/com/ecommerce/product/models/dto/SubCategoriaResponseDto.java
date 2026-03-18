package com.ecommerce.product.models.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class SubCategoriaResponseDto {

	@NotNull
	private Long id;
	@NotBlank
	private String nombre;
	
	
}
