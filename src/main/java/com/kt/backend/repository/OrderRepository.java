package com.kt.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.backend.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
