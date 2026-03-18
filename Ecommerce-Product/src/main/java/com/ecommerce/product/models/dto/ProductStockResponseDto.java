package com.ecommerce.product.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ProductStockResponseDto {

	@NotNull
	private Boolean stockDisponible;
	
	private Integer cantidad;
	
}
