package com.kt.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.backend.entity.Bill;

public interface BillRepository extends JpaRepository<Bill, Integer>{

}
