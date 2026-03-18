package com.ecommerce.product.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.ecommerce.product.models.dto.OrderItemRequestDto;
import com.ecommerce.product.service.IProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockCompensationListener {

	private final IProductService productService;
	
	
	@RabbitListener(queues = "${mq.queue.stock}")
    public void receiveCompensationMessage(OrderItemRequestDto message) {
        log.info("Recibido mensaje de compensación para producto: {}", message.getProductId());        
        try {
           
            productService.increaseStock(message.getProductId(), message.getQuantity());
        } catch (Exception e) {
            log.error("Error crítico restaurando stock: {}", e.getMessage());           
        }
    }
	
}
