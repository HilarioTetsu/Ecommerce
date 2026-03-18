package com.ecommerce.order.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
@EnableConfigurationProperties(MqProperties.class)
public class RabbitMqConfig {

	private final MqProperties mqProperties;
	
	private AmqpAdmin amqpAdmin;
	
	
	public RabbitMqConfig(MqProperties mqProperties, AmqpAdmin amqpAdmin) {
		this.mqProperties = mqProperties;
		this.amqpAdmin = amqpAdmin;
	}
	
	@PostConstruct
    public void init() {        
        amqpAdmin.initialize(); 
    }
	
	@Bean
    public Queue queue() {
        return new Queue(mqProperties.getQueue().getStock(), true); 
    }
	
	@Bean
    public TopicExchange exchange() {
        return new TopicExchange(mqProperties.getExchange());
    }
	
	@Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(mqProperties.getRouting().getStock());
    }
	
	@Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
	
	@Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
	

}
