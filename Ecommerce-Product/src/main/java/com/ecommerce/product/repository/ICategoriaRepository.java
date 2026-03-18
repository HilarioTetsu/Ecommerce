package com.ecommerce.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.product.models.entity.Categoria;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long>{

}
