package com.ecommerce.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.order.models.entity.DetailOrder;

public interface IDetailOrderRepository extends JpaRepository<DetailOrder, Integer> {

}
