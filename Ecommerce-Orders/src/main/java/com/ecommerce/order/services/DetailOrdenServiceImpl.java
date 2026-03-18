package com.ecommerce.order.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.order.config.MqProperties;
import com.ecommerce.order.models.client.IProductClient;
import com.ecommerce.order.models.dto.OrderItemRequestDto;
import com.ecommerce.order.models.dto.ProductResponseDto;
import com.ecommerce.order.models.entity.DetailOrder;

@Service
public class DetailOrdenServiceImpl implements IDetailOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DetailOrdenServiceImpl.class);

    
    private final IProductClient productClient;
    
    private final RabbitTemplate rabbitTemplate;
    
    private final MqProperties mqProps;
    
    public DetailOrdenServiceImpl(IProductClient productClient, RabbitTemplate rabbitTemplate, MqProperties mqProps) {
        this.productClient = productClient;
        this.rabbitTemplate = rabbitTemplate;
        this.mqProps = mqProps;
    }
    
    
    @Override
    @Transactional
    public List<DetailOrder> createDetailOrders(List<OrderItemRequestDto> orderItems) {
        
        List<DetailOrder> detallesListos = new ArrayList<>();
        List<OrderItemRequestDto> itemsProcesadosExitosamente = new ArrayList<>(); 

        for (OrderItemRequestDto item : orderItems) {
            try {
               
                ProductResponseDto productDto = productClient.getProductById(item.getProductId());

               
                productClient.reduceStock(item.getProductId(), item.getQuantity());

               
                itemsProcesadosExitosamente.add(item);

               
                DetailOrder detail = DetailOrder.builder()
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .unitPrice(productDto.getPrecio()) 
                        .build();

                detallesListos.add(detail);

            } catch (Exception e) {
                
                LOGGER.error("Error procesando item {}. Iniciando Rollback parcial...", item.getProductId(), e);

               
                rollbackTransactions(itemsProcesadosExitosamente);

               
                throw new RuntimeException("Fallo al procesar el producto: " + item.getProductId() + 
                                           ". Se ha revertido el stock de los items anteriores.", e);
            }
        }

        return detallesListos;
    }

   
    private void rollbackTransactions(List<OrderItemRequestDto> itemsToRollback) {
        if (itemsToRollback.isEmpty()) return;

        for (OrderItemRequestDto item : itemsToRollback) {
            try {
                OrderItemRequestDto msg = new OrderItemRequestDto();
                msg.setProductId(item.getProductId());
                msg.setQuantity(item.getQuantity());

                
                rabbitTemplate.convertAndSend(
                    mqProps.getExchange(),
                    mqProps.getRouting().getStock(),
                    msg
                );
                LOGGER.info("Enviado rollback para: {}", item.getProductId());
                
            } catch (Exception ex) {
                
                LOGGER.error("FATAL: No se pudo enviar rollback para {}", item.getProductId(), ex);
            }
        }
    }

}