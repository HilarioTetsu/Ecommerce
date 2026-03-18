package com.ecommerce.product.mappers;

import org.springframework.stereotype.Component;

import com.ecommerce.product.models.dto.SubCategoriaRequestDto;
import com.ecommerce.product.models.dto.SubCategoriaResponseDto;
import com.ecommerce.product.models.entity.SubCategoria;

@Component
public class SubCategoriaMapper {

	public SubCategoriaResponseDto toDto(SubCategoria entity) {
        if (entity == null) return null;
        
        
        return SubCategoriaResponseDto.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())        
                .build();
    }
	
	
	public SubCategoria toEntity(SubCategoriaRequestDto dto) {

        return SubCategoria.builder()
        		.id(dto.getId())
        		.nombre(dto.getNombre())
        		.build();
        
        
    }
	
}
