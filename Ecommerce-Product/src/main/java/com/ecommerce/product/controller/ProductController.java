package com.ecommerce.product.controller;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.product.models.dto.ProductRequestDto;
import com.ecommerce.product.models.dto.ProductResponseDto;
import com.ecommerce.product.models.dto.ProductStockResponseDto;
import com.ecommerce.product.service.IProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {

	private final IProductService productService;

	public ProductController(IProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponseDto> getProductById(@PathVariable String id) {

		return ResponseEntity.ok(productService.getById(id));
	}
	

	
	@GetMapping
	public ResponseEntity<Page<ProductResponseDto>> getPageProducts(@RequestParam(defaultValue = "0") Integer pagina,
			@RequestParam(defaultValue = "10") Integer size,
			@RequestParam(required = false) BigDecimal minPrice,
			@RequestParam(required = false) BigDecimal maxPrice,
			@RequestParam(defaultValue = "fecha_creacion,desc;") String sorts) {

		return ResponseEntity.ok(productService.getPageProducts(pagina, size, minPrice, maxPrice, sorts));
	}
	
	@PostMapping
	public ResponseEntity<ProductResponseDto> createProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {

		return ResponseEntity.ok(productService.saveProducto(productRequestDto));
	}
	
	@PatchMapping
	public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {

		return ResponseEntity.ok(productService.updateProducto(productRequestDto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable(required = true) String id) {

		productService.deleteProduct(id);

		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}/stock")
	public ResponseEntity<ProductStockResponseDto> getStockByProductId(@PathVariable String id) {

		return ResponseEntity.ok(productService.getStockByProductId(id));
	}
	
	@PatchMapping("/{id}/reduce-stock")	
	public ResponseEntity<Void> reduceStock(@PathVariable String id, @RequestParam(required = true) Integer cantidad) {

		productService.reduceStock(id, cantidad);

		return ResponseEntity.noContent().build();
	}
	
	
}
