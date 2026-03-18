package com.ecommerce.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.product.models.entity.SubCategoria;

@Repository
public interface ISubCategoriaRepository extends JpaRepository<SubCategoria, Long>{

}
