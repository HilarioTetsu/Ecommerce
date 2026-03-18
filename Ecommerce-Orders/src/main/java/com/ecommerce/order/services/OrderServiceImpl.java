package com.ecommerce.order.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.order.config.MqProperties;
import com.ecommerce.order.mapper.OrderMapper;
import com.ecommerce.order.models.dto.OrderItemRequestDto;
import com.ecommerce.order.models.dto.OrderResponseDto;
import com.ecommerce.order.models.entity.DetailOrder;
import com.ecommerce.order.models.entity.Order;
import com.ecommerce.order.repository.IOrderRepository;

@Service
public class OrderServiceImpl implements IOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    
    private final IOrderRepository orderRepository;
        
    private final IDetailOrderService detailOrderService;
    
    private final OrderMapper orderMapper;
    
    private final RabbitTemplate rabbitTemplate;
    
    private final MqProperties mqProps;
    
    public OrderServiceImpl(IOrderRepository orderRepository, IDetailOrderService detailOrderService, OrderMapper orderMapper, RabbitTemplate rabbitTemplate, MqProperties mqProps) {
                this.orderRepository = orderRepository;
                this.detailOrderService = detailOrderService;
                this.orderMapper = orderMapper;
                this.rabbitTemplate = rabbitTemplate;
                this.mqProps = mqProps;
                
    }
    
    
    @Override
    @Transactional
    public OrderResponseDto createOrder(List<OrderItemRequestDto> orderItems) {
        
        String orderNumber = UUID.randomUUID().toString();
        
         List<DetailOrder> details = detailOrderService.createDetailOrders(orderItems);
         

         Order order = Order.builder()
                        .orderId(orderNumber)
                        .details(new ArrayList<>())
                        .build();
         
         BigDecimal totalPrice = BigDecimal.ZERO;
         
         for(DetailOrder detail : details) {
             
             order.addDetailOrder(detail);
             totalPrice = totalPrice.add(detail.getUnitPrice().multiply(new BigDecimal(detail.getQuantity())));
             
         }
         order.setTotalPrice(totalPrice);
         
         try {
                // Intentamos guardar la orden y sus detalles
                OrderResponseDto savedOrder = orderMapper.toDto(orderRepository.save(order));
                
                
                rabbitTemplate.convertAndSend(
                        mqProps.getExchange(),
                        mqProps.getRouting().getOrderCreated(), // "order.created"
                        savedOrder
                    );
                
                LOGGER.info("Orden creada y mensaje enviado: {}", savedOrder.getOrderId());
                
                return savedOrder;

            } catch (Exception e) {

                LOGGER.error("Error guardando orden: {}. Iniciando rollback de stock...", e.getMessage(), e);
                
                for (OrderItemRequestDto item : orderItems) {
                   
                    OrderItemRequestDto msg = new OrderItemRequestDto();
                    msg.setProductId(item.getProductId());
                    msg.setQuantity(item.getQuantity());

                    
                    rabbitTemplate.convertAndSend(
                        mqProps.getExchange(),      
                        mqProps.getRouting().getStock(), 
                        msg
                    );
                }

             
                throw new RuntimeException("Error interno al procesar la orden. Se ha revertido el stock.", e);
            }
        }

}