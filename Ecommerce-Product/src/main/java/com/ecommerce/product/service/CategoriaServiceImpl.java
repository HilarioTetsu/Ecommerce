package com.ecommerce.product.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.product.mappers.CategoriaMapper;
import com.ecommerce.product.mappers.SubCategoriaMapper;
import com.ecommerce.product.models.dto.CategoriaRequestDto;
import com.ecommerce.product.models.dto.CategoriaResponseDto;
import com.ecommerce.product.models.dto.SubCategoriaRequestDto;
import com.ecommerce.product.models.entity.Categoria;
import com.ecommerce.product.models.entity.SubCategoria;
import com.ecommerce.product.models.enums.Estado;
import com.ecommerce.product.repository.ICategoriaRepository;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

	private final ICategoriaRepository categoriaRepo;

	private final CategoriaMapper mapper;

	private final SubCategoriaMapper subCategoriaMapper;

	public CategoriaServiceImpl(ICategoriaRepository categoriaRepo, CategoriaMapper mapper,
			SubCategoriaMapper subCategoriaMapper) {
		this.categoriaRepo = categoriaRepo;
		this.mapper = mapper;
		this.subCategoriaMapper = subCategoriaMapper;
	}

	@Override
	@Transactional
	public CategoriaResponseDto save(CategoriaRequestDto dto) {

		Categoria categoria = mapper.toEntity(dto);

		return mapper.toDto(categoriaRepo.save(categoria));
	}

	@Override
	@Transactional(readOnly = true)
	public List<CategoriaResponseDto> getAll() {

		return categoriaRepo.findAll().stream().map(c -> mapper.toDto(c)).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public CategoriaResponseDto getById(Long id) {

		Categoria categoria = categoriaRepo.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Categoria no encontrada"));

		return mapper.toDto(categoria);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {

		Categoria categoria = categoriaRepo.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Categoria no encontrada"));

		categoria.getSubCategorias().stream().forEach(s -> {
			s.setStatus(Estado.INACTIVO);
		});

		categoria.setStatus(Estado.INACTIVO);

		categoriaRepo.save(categoria);

	}

	@Override
	@Transactional
	public CategoriaResponseDto updateCategoria(CategoriaRequestDto dto) {
		Categoria categoria = categoriaRepo.findById(dto.getId())
				.orElseThrow(() -> new NoSuchElementException("Categoria no encontrada"));

		categoria.setNombre(dto.getNombre());

		if (dto.getSubCategorias()!=null && dto.getSubCategorias().size()>0) {

			List<SubCategoria> listaActual = categoria.getSubCategorias();
			List<SubCategoriaRequestDto> listaNueva = dto.getSubCategorias();

			for (SubCategoriaRequestDto subDto : listaNueva) {

				Optional<SubCategoria> coincidencia = listaActual.stream()
						.filter(existing -> existing.getNombre().equals(subDto.getNombre())).findFirst();

				if (coincidencia.isEmpty()) {
					categoria.addSubCategoria(subCategoriaMapper.toEntity(subDto));
				}

			}

			List<String> nombresNuevos = listaNueva.stream().map(SubCategoriaRequestDto::getNombre).toList();

			listaActual.stream().filter(existing -> !nombresNuevos.contains(existing.getNombre()))
					.forEach(s -> s.setStatus(Estado.INACTIVO));
			;
		}
		

		return mapper.toDto(categoriaRepo.save(categoria));
	}

}
