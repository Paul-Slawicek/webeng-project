package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.OrderDTO;
import at.technikum.springrestbackend.entity.Order;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            Long userId = userPrincipal.getId();
            Order order = orderService.createOrder(orderDTO.getProductId(), orderDTO.getQuantity(), userId);
            return ResponseEntity.ok("Order placed successfully with ID: " + order.getId());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/my-orders")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMyOrders() {
        try {
            List<Order> orders = orderService.getOrdersForCurrentUser();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        try {
            orderService.updateOrderStatus(id, orderDTO.getStatus());
            return ResponseEntity.ok("Order status updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok("Order deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
