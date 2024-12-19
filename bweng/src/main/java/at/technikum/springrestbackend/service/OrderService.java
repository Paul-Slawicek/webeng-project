package at.technikum.springrestbackend.service;


import at.technikum.springrestbackend.entity.Order;
import at.technikum.springrestbackend.entity.Product;
import at.technikum.springrestbackend.repository.OrderRepository;
import at.technikum.springrestbackend.repository.ProductRepository;
import at.technikum.springrestbackend.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }
    public List<Order> getOrdersForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal(); // Cast to UserPrincipal
        Long userId = userPrincipal.getId(); // Get the user ID
        System.out.println("User ID: " + userId); // Debug log
        return orderRepository.findByUserId(userId);
    }


    public Order createOrder(Long productId, Integer quantity, Long userId) {
        // Validate product existence
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Calculate total price
        double totalPrice = product.getPrice() * quantity;

        // Create and save order
        Order order = new Order(userId, productId, quantity, totalPrice);
        return orderRepository.save(order);
    }
}
