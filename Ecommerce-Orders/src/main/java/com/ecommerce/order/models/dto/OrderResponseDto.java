package com.ecommerce.order.models.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class OrderResponseDto {

	@NotBlank
	private String orderId;
	@NotNull
	private List<DetailOrderResponseDto> details;
	@NotNull
	private BigDecimal totalPrice;
	
	@NotNull
	private LocalDateTime fechaCreacion;
	
}
