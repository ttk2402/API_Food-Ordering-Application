package com.kt.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.backend.dto.BillDto;
import com.kt.backend.response.ApiResponse;
import com.kt.backend.service.BillService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/bill")
public class BillController {
	@Autowired
	private BillService billService;
	
	@PostMapping("/add")
	public ResponseEntity<BillDto> createBill(@RequestBody BillDto billDto) {
		BillDto createBill = this.billService.createBill(billDto);
		return new ResponseEntity<BillDto>(createBill, HttpStatus.CREATED);
	}
	
	@GetMapping("/{billId}")
	public ResponseEntity<BillDto> getCart(@PathVariable Integer billId) {
		BillDto billDto = this.billService.getBill(billId);
		return new ResponseEntity<BillDto>(billDto, HttpStatus.OK);
	}

	@DeleteMapping("/{billId}")
	public ResponseEntity<ApiResponse> deleteBillById(@PathVariable Integer billId) {
		this.billService.deleteBill(billId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Bill is deleted successfully!", true),
				HttpStatus.OK);
	}	
}