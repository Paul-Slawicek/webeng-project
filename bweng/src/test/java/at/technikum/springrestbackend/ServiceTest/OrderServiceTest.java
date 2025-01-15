package at.technikum.springrestbackend.ServiceTest;

import at.technikum.springrestbackend.entity.Order;
import at.technikum.springrestbackend.entity.Product;
import at.technikum.springrestbackend.repository.OrderRepository;
import at.technikum.springrestbackend.repository.ProductRepository;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository, productRepository);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testGetOrdersForCurrentUser() {
        UserPrincipal userPrincipal = new UserPrincipal(1L, "testuser", "password", "user");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userPrincipal);
        when(orderRepository.findByUserId(1L)).thenReturn(List.of(new Order(1L, 2L, 1, 100.0)));

        List<Order> orders = orderService.getOrdersForCurrentUser();

        assertEquals(1, orders.size());
        verify(orderRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testCreateOrder() {
        Long productId = 2L;
        int quantity = 2;
        double productPrice = 50.0;
        Long userId = 1L;

        Product product = new Product();
        product.setId(productId);
        product.setPrice(productPrice);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order order = orderService.createOrder(productId, quantity, userId);

        assertNotNull(order);
        assertEquals(userId, order.getUserId());
        assertEquals(productId, order.getProductId());
        assertEquals(100.0, order.getTotalPrice());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testGetAllOrders() {
        // Arrange
        Order order1 = new Order();
        order1.setId(1L);
        order1.setStatus("pending");

        Order order2 = new Order();
        order2.setId(2L);
        order2.setStatus("completed");

        when(orderRepository.findAll()).thenReturn(List.of(order1, order2));

        // Act
        List<Order> orders = orderService.getAllOrders();

        // Assert
        assertEquals(2, orders.size());
        assertEquals("pending", orders.get(0).getStatus());
        assertEquals("completed", orders.get(1).getStatus());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testUpdateOrderStatus() {
        // Arrange
        Long orderId = 1L;
        String newStatus = "completed";

        Order order = new Order();
        order.setId(orderId);
        order.setStatus("pending");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        // Act
        orderService.updateOrderStatus(orderId, newStatus);

        // Assert
        assertEquals(newStatus, order.getStatus());
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testUpdateOrderStatusNotFound() {
        // Arrange
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.updateOrderStatus(orderId, "completed");
        });
        assertEquals("Order not found", exception.getMessage());
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testDeleteOrder() {
        // Arrange
        Long orderId = 1L;

        Order order = new Order();
        order.setId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        doNothing().when(orderRepository).delete(order);

        // Act
        orderService.deleteOrder(orderId);

        // Assert
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).delete(order);
    }

    @Test
    void testDeleteOrderNotFound() {
        // Arrange
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.deleteOrder(orderId);
        });
        assertEquals("Order not found", exception.getMessage());
        verify(orderRepository, times(1)).findById(orderId);
    }
}
