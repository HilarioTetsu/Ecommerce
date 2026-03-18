package com.ecommerce.product.models.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CategoriaResponseDto {

	@NotNull
	private Long id;
	@NotBlank
	private String nombre;
	@NotNull
	private List<SubCategoriaResponseDto> subCategorias;
	
}
