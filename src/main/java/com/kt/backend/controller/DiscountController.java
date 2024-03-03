package com.kt.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.backend.dto.DiscountDto;
import com.kt.backend.entity.Discount;
import com.kt.backend.service.DiscountService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/discount")
public class DiscountController {

	@Autowired
	private DiscountService discountService;
	
	@PostMapping("/add")
	public ResponseEntity<DiscountDto> createDiscount(@RequestBody DiscountDto discountDto) {
		DiscountDto createDis = this.discountService.createDiscount(discountDto);
		return new ResponseEntity<DiscountDto>(createDis, HttpStatus.CREATED);
	}
	
	@GetMapping("/{code}")
	public ResponseEntity<Optional<Discount>> getDiscountByCode(@PathVariable String code) {
		Optional<Discount> dis = this.discountService.findDiscount(code);
		return new ResponseEntity<Optional<Discount>>(dis, HttpStatus.OK);
	}
	
	@GetMapping("/current")
	public ResponseEntity<Optional<List<Discount>>> getDiscountCurrent() {
		Optional<List<Discount>> discounts = this.discountService.getListDiscountCurrent();
		return new ResponseEntity<Optional<List<Discount>>>(discounts, HttpStatus.OK);
	}
	
	@PutMapping("/{discountId}")
	public ResponseEntity<DiscountDto> changeStatusDiscount(@PathVariable Integer discountId) {
		DiscountDto updatedDis = this.discountService.changeStatusDiscount(discountId);
		return new ResponseEntity<DiscountDto>(updatedDis, HttpStatus.OK);
	}
}
