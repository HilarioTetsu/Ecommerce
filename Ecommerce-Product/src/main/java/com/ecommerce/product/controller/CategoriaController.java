package com.ecommerce.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.product.models.dto.CategoriaRequestDto;
import com.ecommerce.product.models.dto.CategoriaResponseDto;
import com.ecommerce.product.service.ICategoriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

	private final ICategoriaService categoriaService;

	public CategoriaController(ICategoriaService categoriaService) {
		this.categoriaService = categoriaService;
	}
	
	
	@PostMapping
	public ResponseEntity<CategoriaResponseDto> crearCategoria(@RequestBody @Valid CategoriaRequestDto dto){
		

	 return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.save(dto));	
	}
	
	@GetMapping
	public ResponseEntity<List<CategoriaResponseDto>> getAll(){
		
		return ResponseEntity.ok(categoriaService.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoriaResponseDto> getCategoriaById(@PathVariable Long id){
		
		return ResponseEntity.ok(categoriaService.getById(id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategoriaById(@PathVariable Long id){
		
		categoriaService.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<CategoriaResponseDto> updateCategoria(@RequestBody @Valid CategoriaRequestDto dto){
		
		if (dto.getId()==null) throw new IllegalArgumentException("Identificador no disponible");
		
		return ResponseEntity.ok(categoriaService.updateCategoria(dto));
	}
	
	
}
