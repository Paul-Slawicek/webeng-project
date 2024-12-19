package at.technikum.springrestbackend.service;


import at.technikum.springrestbackend.entity.Order;
import at.technikum.springrestbackend.entity.Product;
import at.technikum.springrestbackend.repository.OrderRepository;
import at.technikum.springrestbackend.repository.ProductRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
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
