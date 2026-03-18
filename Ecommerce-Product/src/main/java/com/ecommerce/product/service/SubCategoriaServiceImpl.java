package com.ecommerce.product.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.product.mappers.SubCategoriaMapper;
import com.ecommerce.product.models.dto.SubCategoriaRequestDto;
import com.ecommerce.product.models.entity.SubCategoria;
import com.ecommerce.product.repository.ISubCategoriaRepository;

@Service
public class SubCategoriaServiceImpl implements ISubCategoriaService {

	private final ISubCategoriaRepository subCategoriaRepo;
	
	private final SubCategoriaMapper mapper;

	
	
	public SubCategoriaServiceImpl(ISubCategoriaRepository subCategoriaRepo, SubCategoriaMapper mapper) {
		this.subCategoriaRepo = subCategoriaRepo;
		this.mapper = mapper;
	}



	@Override
	@Transactional
	public SubCategoria save(SubCategoriaRequestDto dto) {
		
		SubCategoria sub = mapper.toEntity(dto);
					
		return subCategoriaRepo.save(sub);
	}





	@Override
	@Transactional
	public List<SubCategoria> saveAll(List<SubCategoriaRequestDto> dtos) {
		
		List<SubCategoria> subCategorias = dtos.stream().map(s -> mapper.toEntity(s) ).collect(Collectors.toList());
		
		return subCategoriaRepo.saveAll(subCategorias);
	}



	@Override
	@Transactional
	public List<SubCategoria> saveAllEntities(List<SubCategoria> subCategorias) {
		
		return subCategoriaRepo.saveAll(subCategorias);
	}



	@Override
	public SubCategoria findById(Long Id) {
		
		return subCategoriaRepo.findById(Id).orElseThrow(() -> new NoSuchElementException("Categoria no encontrada"));
	}
	
	
	
}
