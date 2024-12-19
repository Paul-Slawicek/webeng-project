package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.OrderDTO;
import at.technikum.springrestbackend.entity.Order;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/place")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> placeOrder(@RequestBody OrderDTO orderDTO, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            Long userId = userPrincipal.getId(); // Extract the user ID
            Order order = orderService.createOrder(orderDTO.getProductId(), orderDTO.getQuantity(), userId);
            return ResponseEntity.ok("Order placed successfully with ID: " + order.getId());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

}
