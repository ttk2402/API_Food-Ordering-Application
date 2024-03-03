package com.kt.backend.service.Impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.backend.dto.BillDto;
import com.kt.backend.dto.OrderDto;
import com.kt.backend.dto.ResOrderDto;
import com.kt.backend.entity.Account;
import com.kt.backend.entity.Bill;
import com.kt.backend.entity.CheckOut;
import com.kt.backend.entity.Item;
import com.kt.backend.entity.Order;
import com.kt.backend.entity.OrderStatus;
import com.kt.backend.exception.ResourceNotFoundException;
import com.kt.backend.repository.AccountRepository;
import com.kt.backend.repository.CheckOutRepository;
import com.kt.backend.repository.ItemRepository;
import com.kt.backend.repository.OrderRepository;
import com.kt.backend.repository.OrderStatusRepository;
import com.kt.backend.service.BillService;
import com.kt.backend.service.CartService;
import com.kt.backend.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CheckOutRepository checkOutRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private BillService billService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderStatusRepository orderStatusRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ResOrderDto createOrder(OrderDto orderDto, Integer accountId, Integer checkoutId) {
		Account account = this.accountRepository.findById(accountId).orElseThrow(()-> new ResourceNotFoundException("Account","AccountId", accountId));
		CheckOut checkout = this.checkOutRepository.findById(checkoutId).orElseThrow(()-> new ResourceNotFoundException("CheckOut","CheckOutId", checkoutId));
		Order order = this.modelMapper.map(orderDto, Order.class);
		order.setAccount(account);
		order.setCheckout(checkout);
		order.setItems(this.itemRepository.findItemsCurrentByCart(account.getCart().getId()));
		order.setTotalprice(this.cartService.getTotalPriceOfCartCurrent(account.getCart().getId()));
		BillDto billDto = new BillDto();
		billDto.setIssuedate(order.getOrderdate());
		billDto.setTotalprice(order.getTotalprice());
		BillDto responseBill = this.billService.createBill(billDto);		
		Bill bill = this.modelMapper.map(responseBill, Bill.class);
		order.setBill(bill);	
		OrderStatus orStatus = this.orderStatusRepository.findOrderStatusByStatus("Processing");
		order.setOrder_status(orStatus);
		Order addOrder = this.orderRepository.save(order);
		 // Update orderId for items in the list
	    List<Item> items = order.getItems();
	    for (Item item : items) {
	        item.setOrder(order); // Assuming there's a setter for orderId in the Item class
	    }
	    // Save the updated items back to the repository if needed
	    this.itemRepository.saveAll(items);
		return this.modelMapper.map(addOrder, ResOrderDto.class);
	}

	@Override
	public OrderDto getOrder(Integer orderId) {
		Order order = this.orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","OrderId", orderId));
		return this.modelMapper.map(order, OrderDto.class);
	}

	@Override
	public void deleteOrder(Integer orderId) {
		Order order = this.orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","OrderId", orderId));
		Integer billId = order.getBill().getId();
		this.orderRepository.delete(order);
		this.billService.deleteBill(billId);
	} 
	

}
