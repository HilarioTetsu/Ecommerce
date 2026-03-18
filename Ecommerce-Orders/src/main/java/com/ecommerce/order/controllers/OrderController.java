package com.ecommerce.order.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.order.models.dto.OrderItemRequestDto;
import com.ecommerce.order.models.dto.OrderResponseDto;
import com.ecommerce.order.services.IOrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	private final IOrderService orderService;
	
	public OrderController(IOrderService orderService) {
		this.orderService = orderService;
	}
	
	@PostMapping
	public ResponseEntity<OrderResponseDto> createOrder(@RequestBody List<OrderItemRequestDto> orderItems) {
		return ResponseEntity.ok(orderService.createOrder(orderItems));
	}
	
}
