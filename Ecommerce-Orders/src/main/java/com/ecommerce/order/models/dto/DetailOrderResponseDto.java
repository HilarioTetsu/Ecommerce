package com.ecommerce.order.models.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class DetailOrderResponseDto {

	@NotNull
	private Integer id;
	@NotBlank
	private String productId;
	@NotNull
	private BigDecimal unitPrice;
	@NotNull
	private Integer quantity;

	
}
