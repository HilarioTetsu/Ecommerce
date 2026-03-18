package com.ecommerce.product.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.product.models.dto.ProductStockResponseDto;
import com.ecommerce.product.models.entity.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, String>{

	
	String QUERY = "SELECT p.* FROM products p ";
	
	String FILTERS = """
			WHERE (:minPrice IS NULL OR p.precio >= :minPrice)
			AND (:maxPrice IS NULL OR p.precio <= :maxPrice)
			AND p.status=:status 					
			""";
	
	@NativeQuery(value = QUERY + FILTERS, countQuery = "SELECT COUNT(*) FROM products p " + FILTERS)
	Page<Product> findProductsByPrecioBetweenAndStatus(@Param("minPrice") BigDecimal minPrice,
			@Param("maxPrice") BigDecimal maxPrice,
			@Param("status") Integer integer,
			Pageable pageable);

	
	
	@NativeQuery(value = """ 
			
	select
		case
			when p.stock > 0 then true
			else false
		end as stockDisponible,
		p.stock as cantidad
	from
		products p
	where
		p.id = ?1
		and p.status = ?2;
			
			""")
	Optional<ProductStockResponseDto> findStockByProductId(String id, Integer status);


	@Transactional
	@Modifying
	@NativeQuery(value = """
			
			UPDATE products p
			         SET stock = p.stock - :cant 
            WHERE p.id = :id
			         AND p.stock >= :cant
			         AND p.status = :status
			""")
	Integer reduceStock(@Param("id") String id, @Param("cant") Integer cantidad, @Param("status") Integer status);


	@Transactional
	@Modifying
	@NativeQuery(value = """
			
			UPDATE products p
			         SET stock = p.stock + :cant 
            WHERE p.id = :id			         
			         AND p.status = :status
			""")
	Integer increaseStock(@Param("id") String id, @Param("cant") Integer cantidad, @Param("status") Integer status);

}
