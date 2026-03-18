package com.ecommerce.product.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;

import com.ecommerce.product.models.dto.ProductRequestDto;
import com.ecommerce.product.models.dto.ProductResponseDto;
import com.ecommerce.product.models.dto.ProductStockResponseDto;

public interface IProductService {

	List<ProductResponseDto> getAll();
	
	void deleteProduct(String id);
	
	ProductResponseDto getById(String id);
	
	ProductResponseDto updateProducto(ProductRequestDto dto);
	
	ProductResponseDto saveProducto(ProductRequestDto dto);

	Page<ProductResponseDto> getPageProducts(Integer pagina, Integer size, BigDecimal minPrice, BigDecimal maxPrice, String sorts);

	ProductStockResponseDto getStockByProductId(String id);

	void reduceStock(String id, Integer cantidad);
	
	void increaseStock(String id, Integer cantidad);

	
}
