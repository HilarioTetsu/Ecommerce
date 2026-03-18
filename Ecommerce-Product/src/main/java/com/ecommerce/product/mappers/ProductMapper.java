package com.ecommerce.product.mappers;

import org.springframework.stereotype.Component;

import com.ecommerce.product.models.dto.ProductRequestDto;
import com.ecommerce.product.models.dto.ProductResponseDto;
import com.ecommerce.product.models.entity.Product;

@Component
public class ProductMapper {

	private final SubCategoriaMapper subCategoriaMapper;
	
	
	
	
	public ProductMapper(SubCategoriaMapper subCategoriaMapper) {
		this.subCategoriaMapper = subCategoriaMapper;
	}




	public Product toEntity(ProductRequestDto dto) {
				
		return Product.builder()
				.id(dto.getId())
				.nombre(dto.getNombre())
				.desc(dto.getDesc())
				.precio(dto.getPrecio())
				.subCategoria(subCategoriaMapper.toEntity(dto.getSubCategoria()))
				.stock(dto.getStock())
				.build();
		
	}
	
	public ProductResponseDto toDto(Product product) {
		
		return ProductResponseDto.builder()
				.id(product.getId())
				.nombre(product.getNombre())
				.desc(product.getDesc())
				.precio(product.getPrecio())
				.stock(product.getStock())
				.subCategoria(subCategoriaMapper.toDto(product.getSubCategoria()))
				.build();
		
	}
	
}
