package com.ecommerce.product.service;

import java.util.List;

import com.ecommerce.product.models.dto.CategoriaRequestDto;
import com.ecommerce.product.models.dto.CategoriaResponseDto;

public interface ICategoriaService {

	CategoriaResponseDto save(CategoriaRequestDto dto);

	List<CategoriaResponseDto> getAll();

	CategoriaResponseDto getById(Long id);

	void deleteById(Long id);

	CategoriaResponseDto updateCategoria(CategoriaRequestDto dto);
	
}
