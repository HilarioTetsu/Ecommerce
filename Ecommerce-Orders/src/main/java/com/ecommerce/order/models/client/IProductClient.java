package com.ecommerce.order.models.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.order.models.dto.ProductResponseDto;



@FeignClient(name = "Ecommerce-Product")
public interface IProductClient {

	@PatchMapping("/product/{id}/reduce-stock")
    ResponseEntity<Void> reduceStock(@PathVariable String id, @RequestParam Integer cantidad);
	
	@GetMapping("/product/{id}")
	ProductResponseDto getProductById(@PathVariable String id);
}
