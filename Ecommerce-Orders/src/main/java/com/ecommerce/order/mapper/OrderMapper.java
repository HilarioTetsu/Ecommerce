package com.ecommerce.order.mapper;

import org.springframework.stereotype.Component;

import com.ecommerce.order.models.dto.OrderResponseDto;
import com.ecommerce.order.models.entity.Order;

@Component
public class OrderMapper {

	private final DetailOrderMapper detailOrderMapper;
	
	public OrderMapper(DetailOrderMapper detailOrderMapper) {
		this.detailOrderMapper = detailOrderMapper;
	}
	
	public OrderResponseDto toDto(Order order) {
		return OrderResponseDto.builder()
				.orderId(order.getOrderId())
				.details(order.getDetails().stream()
						.map(dto -> detailOrderMapper.toDto(dto))
						.toList())
				.totalPrice(order.getTotalPrice())
				.fechaCreacion(order.getFechaCreacion())
				.build();
	}
	
}
