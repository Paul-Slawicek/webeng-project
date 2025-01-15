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
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();

        List<Order> orders = orderRepository.findByUserId(userId);

        orders.forEach(order -> {
            Product product = productRepository.findById(order.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            order.setProductTitle(product.getTitle());
        });

        return orders;
    }

    public Order createOrder(Long productId, Integer quantity, Long userId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        double totalPrice = product.getPrice() * quantity;

        Order order = new Order(userId, productId, quantity, totalPrice);
        order.setStatus("pending");
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
    }

    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        orderRepository.delete(order);
    }
}
