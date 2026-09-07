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


    // OrderService.createOrder — validation only, no deduction, no StockOutward
    @Transactional
    public OrderDto createOrder(CreateOrderRequestDto request) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);
        order.setTotalAmount(0.0);
        order = orderRepository.save(order);

        double totalAmount = 0.0;

        for (OrderItemRequestDto itemReq : request.getItems()) {
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

    // OrderService.cancelOrder — nothing to restore now, deduction hasn't happened yet
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

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    // OrderService.completeOrder — re-validate, deduct, log StockOutward, then flip status
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

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

        // fresh check — stock may have moved since the order was created
        for (OrderItem item : orderItems) {
            List<MenuItemRawMaterial> recipe =
                    menuItemRawMaterialRepository.findByMenuItemId(item.getMenuItem().getId());
            for (MenuItemRawMaterial rm : recipe) {
                double requiredQty = rm.getQuantityRequired() * item.getQuantity();
                if (rm.getRawMaterial().getQuantity() < requiredQty) {
                    throw new IllegalStateException("Insufficient stock for " + rm.getRawMaterial().getName());
                }
            }
        }

        for (OrderItem item : orderItems) {
            List<MenuItemRawMaterial> recipe =
                    menuItemRawMaterialRepository.findByMenuItemId(item.getMenuItem().getId());
            for (MenuItemRawMaterial rm : recipe) {
                RawMaterial rawMaterial = rm.getRawMaterial();
                double usedQty = rm.getQuantityRequired() * item.getQuantity();

                rawMaterial.setQuantity(rawMaterial.getQuantity() - usedQty);
                rawMaterialRepository.save(rawMaterial);

                StockOutward outward = new StockOutward();
                outward.setOrder(order);
                outward.setRawMaterial(rawMaterial);
                outward.setQuantityUsed(usedQty);
                outward.setUsedDate(LocalDateTime.now());
                stockOutwardRepository.save(outward);
            }
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