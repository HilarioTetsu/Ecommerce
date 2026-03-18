package com.ecommerce.order.services;

import java.util.List;

import com.ecommerce.order.models.dto.OrderItemRequestDto;
import com.ecommerce.order.models.dto.OrderResponseDto;

public interface IOrderService {

	OrderResponseDto createOrder(List<OrderItemRequestDto> orderItems);
	
}
