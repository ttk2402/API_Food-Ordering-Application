package com.kt.backend.service;

import com.kt.backend.dto.OrderDto;
import com.kt.backend.dto.ResOrderDto;

public interface OrderService {

	ResOrderDto createOrder(OrderDto orderDto, Integer accountId, Integer checkoutId);
	
	OrderDto getOrder(Integer orderId);
	
	void deleteOrder(Integer orderId);
	
}
