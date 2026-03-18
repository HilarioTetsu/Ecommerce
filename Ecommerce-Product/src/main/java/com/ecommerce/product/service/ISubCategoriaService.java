package com.ecommerce.product.service;

import java.util.List;

import com.ecommerce.product.models.dto.SubCategoriaRequestDto;
import com.ecommerce.product.models.entity.SubCategoria;

public interface ISubCategoriaService {

	SubCategoria save(SubCategoriaRequestDto dto);
	
	SubCategoria findById(Long Id);
			
	List<SubCategoria> saveAll(List<SubCategoriaRequestDto> dtos);
	
	List<SubCategoria> saveAllEntities(List<SubCategoria> subCategorias);
}
