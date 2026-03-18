package com.ecommerce.order.services;

import java.util.List;

import com.ecommerce.order.models.dto.OrderItemRequestDto;
import com.ecommerce.order.models.entity.DetailOrder;

public interface IDetailOrderService {

	
	List<DetailOrder> createDetailOrders(List<OrderItemRequestDto> orderItems);
	
}
