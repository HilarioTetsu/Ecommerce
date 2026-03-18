package com.ecommerce.product.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.product.mappers.ProductMapper;
import com.ecommerce.product.models.dto.ProductRequestDto;
import com.ecommerce.product.models.dto.ProductResponseDto;
import com.ecommerce.product.models.dto.ProductStockResponseDto;
import com.ecommerce.product.models.entity.Product;
import com.ecommerce.product.models.entity.SubCategoria;
import com.ecommerce.product.models.enums.Estado;
import com.ecommerce.product.repository.IProductRepository;
import com.ecommerce.product.util.Utils;

@Service
public class ProductServiceImpl implements IProductService{

	
	private final IProductRepository productRepo;
	
	private final ISubCategoriaService subCategoriaService;
		
	private final ProductMapper mapper;
	
	
	

	

	public ProductServiceImpl(IProductRepository productRepo, ISubCategoriaService subCategoriaService,
			ProductMapper mapper) {
		this.productRepo = productRepo;
		this.subCategoriaService = subCategoriaService;
		this.mapper = mapper;
	}

	@Override
	public List<ProductResponseDto> getAll() {
		
		return productRepo.findAll().stream()
				.filter(p -> p.getStatus().equals(Estado.ACTIVO))
				.map(p -> mapper.toDto(p)).toList();
	}

	@Override
	@Transactional
	public void deleteProduct(String id) {
		
		if (id==null) {
			throw new IllegalArgumentException("Identificador no disponible");
		}
		
		Product producto = productRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));
		
		producto.setStatus(Estado.INACTIVO);
		
		productRepo.save(producto);
		
	}

	@Override
	@Transactional(readOnly = true)
	public ProductResponseDto getById(String id) {
		
		Product producto = productRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));
		
		return mapper.toDto(producto);
	}

	@Override
	@Transactional
	public ProductResponseDto updateProducto(ProductRequestDto dto) {
		
		if (dto.getId()==null) {
			throw new IllegalArgumentException("Identificador no disponible");
		}
		
		if (dto.getSubCategoria().getId()==null) {
			throw new IllegalArgumentException("Identificador no disponible");
		}
		
		Product producto = productRepo.findById(dto.getId()).orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));
		
		SubCategoria subCategoria = subCategoriaService.findById(dto.getSubCategoria().getId());
		
		
		producto.setNombre(dto.getNombre());
		producto.setDesc(dto.getDesc());
		producto.setPrecio(dto.getPrecio());
		producto.setStock(dto.getStock());
		producto.setSubCategoria(subCategoria);
		
		
		return mapper.toDto(productRepo.save(producto));
	}

	@Override
	@Transactional
	public ProductResponseDto saveProducto(ProductRequestDto dto) {
		
		if (dto.getSubCategoria().getId()==null) {
			throw new IllegalArgumentException("Identificador no disponible");
		}
		
		SubCategoria subCategoria = subCategoriaService.findById(dto.getSubCategoria().getId());
		
		Product product = Product.builder()
						  .id(UUID.randomUUID().toString())
						  .nombre(dto.getNombre())
						  .desc(dto.getDesc())
						  .precio(dto.getPrecio())
						  .stock(dto.getStock())
						  .subCategoria(subCategoria)
						  .build();
						
		
		return mapper.toDto(productRepo.save(product));
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ProductResponseDto> getPageProducts(Integer pagina, Integer size, BigDecimal minPrice,
			BigDecimal maxPrice, String sorts) {
		
		Pageable pageable = PageRequest.of(pagina, size, Utils.parseSortParams(sorts));
		
		Page<Product> pageProducts = productRepo.findProductsByPrecioBetweenAndStatus(minPrice, maxPrice, Estado.ACTIVO.getCodigo(), pageable);
		
		return pageProducts.map(p -> mapper.toDto(p));
	}

	@Override
	@Transactional(readOnly = true)
	public ProductStockResponseDto getStockByProductId(String id) {
		
		ProductStockResponseDto stockDto = productRepo.findStockByProductId(id, Estado.ACTIVO.getCodigo())
				.orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));
		
		
		return stockDto;
	}

	@Override
	public void reduceStock(String id, Integer cantidad) {
		
		Integer afectadas = productRepo.reduceStock(id, cantidad, Estado.ACTIVO.getCodigo());
		
		if (afectadas==0) {
			throw new IllegalStateException("No se pudo reducir el stock, verifique la cantidad solicitada y el estado del producto");
		}
		
	}

	@Override
	public void increaseStock(String id, Integer cantidad) {
		
		Integer afectadas = productRepo.increaseStock(id, cantidad, Estado.ACTIVO.getCodigo());
		
		if (afectadas==0) {
			throw new IllegalStateException("No se pudo incrementar el stock, verifique el estado del producto");
		}
		
	}



}
