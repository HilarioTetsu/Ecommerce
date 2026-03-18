package com.ecommerce.product.models.dto;




import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDto {

	@NotNull
	@Length(min = 36, max = 36)
	private String productId;
	
	@NotNull
	private Integer quantity;
	
}
