package com.ecommerce.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.order.models.entity.Order;

public interface IOrderRepository extends JpaRepository<Order, String> {

}
