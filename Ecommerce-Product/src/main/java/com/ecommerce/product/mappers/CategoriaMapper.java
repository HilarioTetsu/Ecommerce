package com.ecommerce.product.mappers;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.ecommerce.product.models.dto.CategoriaRequestDto;
import com.ecommerce.product.models.dto.CategoriaResponseDto;
import com.ecommerce.product.models.entity.Categoria;
import com.ecommerce.product.models.entity.SubCategoria;
import com.ecommerce.product.models.enums.Estado;

@Component
public class CategoriaMapper {

	
	private final SubCategoriaMapper subCategoriaMapper;
	
	
	
	
	public CategoriaMapper(SubCategoriaMapper subCategoriaMapper) {		
		this.subCategoriaMapper = subCategoriaMapper;
	}




	public CategoriaResponseDto toDto(Categoria categoria) {
		
		return CategoriaResponseDto.builder()
				.id(categoria.getId())
				.nombre(categoria.getNombre())
				.subCategorias(categoria
						.getSubCategorias()
						.stream()
						.filter(s -> s.getStatus().equals(Estado.ACTIVO))
						.map(s -> subCategoriaMapper.toDto(s) )
						.toList()
				).build();
				
		
	}
	
	public Categoria toEntity(CategoriaRequestDto dto) {
		
		
		Categoria categoria = Categoria.builder()
				.id(dto.getId())
	            .nombre(dto.getNombre())	            
	            .subCategorias(new ArrayList<>()) 
	            .build();

	   
	    if (dto.getSubCategorias() != null) {
	        dto.getSubCategorias().forEach(subDto -> {
	            SubCategoria subEntity = subCategoriaMapper.toEntity(subDto);
	            categoria.addSubCategoria(subEntity); 
	        });
	    }

	    return categoria;
	}
	
	
}
