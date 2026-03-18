package com.ecommerce.product.models.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaRequestDto {

	private Long id;
	
	@NotBlank	
	private String nombre;
	
	private List<SubCategoriaRequestDto> subCategorias;
	
}
