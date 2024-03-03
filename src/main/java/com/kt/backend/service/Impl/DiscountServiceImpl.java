package com.kt.backend.service.Impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.backend.dto.DiscountDto;
import com.kt.backend.entity.Discount;
import com.kt.backend.exception.ResourceNotFoundException;
import com.kt.backend.repository.DiscountRepository;
import com.kt.backend.service.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService{

	@Autowired
	private DiscountRepository discountRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public DiscountDto createDiscount(DiscountDto discountDto) {
		Discount dis = this.modelMapper.map(discountDto, Discount.class);
		Discount addDis = this.discountRepository.save(dis);
		return this.modelMapper.map(addDis, DiscountDto.class);
	}

	@Override
	public Optional<Discount> findDiscount(String code) {
		Discount dis = this.discountRepository.findDiscountByCode(code);
		return Optional.ofNullable(dis);
	}

	@Override
	public Optional<List<Discount>> getListDiscountCurrent() {
		List<Discount> discounts = this.discountRepository.findDiscountCurrent();
		return Optional.ofNullable(discounts);
	}

	@Override
	public DiscountDto changeStatusDiscount(Integer discountId) {		
		Discount dis = this.discountRepository.findById(discountId).orElseThrow(()-> new ResourceNotFoundException("Discount ","DiscountId", discountId));
		dis.setIsExist(!dis.getIsExist());
		Discount updatedDis = this.discountRepository.save(dis);
		return this.modelMapper.map(updatedDis, DiscountDto.class);
	}
}
