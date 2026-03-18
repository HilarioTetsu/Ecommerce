package com.ecommerce.product.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data 
@ConfigurationProperties(prefix = "mq")
public class MqProperties {

	private String exchange;
    private Routing routing = new Routing();
    private Queue queue = new Queue();
    
    
    @Data
    public static class Routing {
        private String stock; // mq.routing.stock
    }

    @Data
    public static class Queue {
        private String stock; // mq.queue.stock
    }
	
}
