package com.cafestorage.InventoryManagementSystem.service;

import com.cafestorage.InventoryManagementSystem.dto.OrderDto;
import com.cafestorage.InventoryManagementSystem.entity.*;
import com.cafestorage.InventoryManagementSystem.exception.ResourceNotFoundException;
import com.cafestorage.InventoryManagementSystem.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import com.cafestorage.InventoryManagementSystem.dto.CreateOrderRequestDto;
import com.cafestorage.InventoryManagementSystem.dto.OrderItemRequestDto;


@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MenuItemRepository menuItemRepository;
    private final MenuItemRawMaterialRepository menuItemRawMaterialRepository;
    private final RawMaterialRepository rawMaterialRepository;
    private final StockOutwardRepository stockOutwardRepository;
    private final ModelMapper modelMapper;


    @Transactional
    public OrderDto createOrder(CreateOrderRequestDto request) {

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);
        order.setTotalAmount(0.0);
        order = orderRepository.save(order);


        double totalAmount = 0.0;

        for (OrderItemRequestDto itemReq : request.getItems()) { // one menu item per iteration

            MenuItem menuItem = menuItemRepository.findById(itemReq.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

            if (!menuItem.getAvailable()) {
                throw new IllegalStateException("Menu item not available");
            }

            List<MenuItemRawMaterial> recipe =
                    menuItemRawMaterialRepository.findByMenuItemId(menuItem.getId());

            if (recipe.isEmpty()) {
                throw new IllegalStateException("Recipe not defined for menu item");
            }

            // Stock validation
            for (MenuItemRawMaterial rm : recipe) {
                double requiredQty = rm.getQuantityRequired() * itemReq.getQuantity();
                if (rm.getRawMaterial().getQuantity() < requiredQty) {
                    throw new IllegalStateException("Insufficient stock");
                }
            }


            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setPrice(menuItem.getPrice());
            orderItemRepository.save(orderItem);


            for (MenuItemRawMaterial rm : recipe) {

                RawMaterial rawMaterial = rm.getRawMaterial();
                double usedQty = rm.getQuantityRequired() * itemReq.getQuantity();

                rawMaterial.setQuantity(rawMaterial.getQuantity() - usedQty);
                rawMaterialRepository.save(rawMaterial);

                StockOutward outward = new StockOutward();
                outward.setOrder(order);
                outward.setRawMaterial(rawMaterial);
                outward.setQuantityUsed(usedQty);
                outward.setUsedDate(LocalDateTime.now());

                stockOutwardRepository.save(outward);
            }

            totalAmount += menuItem.getPrice() * itemReq.getQuantity();
        }


        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        return modelMapper.map(order, OrderDto.class);
    }



    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return modelMapper.map(order, OrderDto.class);
    }

    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order already cancelled");
        }

        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel a completed order");
        }

        List<StockOutward> stockOutwards =
                stockOutwardRepository.findByOrderId(orderId);

        for (StockOutward outward : stockOutwards) {
            RawMaterial rawMaterial = outward.getRawMaterial();
            rawMaterial.setQuantity(
                    rawMaterial.getQuantity() + outward.getQuantityUsed()
            );
            rawMaterialRepository.save(rawMaterial);
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Transactional
    public void completeOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot complete a cancelled order");
        }

        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Order already completed");
        }

        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .toList();
    }
}