package com.ecommerce.order.mapper;

import org.springframework.stereotype.Component;

import com.ecommerce.order.models.dto.DetailOrderResponseDto;
import com.ecommerce.order.models.entity.DetailOrder;

@Component
public class DetailOrderMapper {

	
	public DetailOrderResponseDto toDto(DetailOrder detailOrder) {
		return DetailOrderResponseDto.builder()
				.id(detailOrder.getId())				
				.productId(detailOrder.getProductId())
				.quantity(detailOrder.getQuantity())
				.unitPrice(detailOrder.getUnitPrice())
				.build();
	}
	
	
}
