package at.technikum.springrestbackend.ControllerTest;

import at.technikum.springrestbackend.controller.OrderController;
import at.technikum.springrestbackend.dto.OrderDTO;
import at.technikum.springrestbackend.entity.Order;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlaceOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setProductId(1L);
        orderDTO.setQuantity(2);

        Order order = new Order();
        order.setId(1L);

        when(orderService.createOrder(1L, 2, 1L)).thenReturn(order);

        var response = orderController.placeOrder(orderDTO, new UserPrincipal(1L, "testuser", "password", "user"));

        assertEquals("Order placed successfully with ID: 1", response.getBody());
        verify(orderService, times(1)).createOrder(1L, 2, 1L);
    }

    @Test
    void testGetMyOrders() {
        List<Order> orders = List.of(new Order());
        when(orderService.getOrdersForCurrentUser()).thenReturn(orders);

        var response = orderController.getMyOrders();

        assertEquals(orders, response.getBody());
        verify(orderService, times(1)).getOrdersForCurrentUser();
    }

    @Test
    void testPlaceOrderWithException() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setProductId(1L);
        orderDTO.setQuantity(2);

        when(orderService.createOrder(1L, 2, 1L)).thenThrow(new IllegalArgumentException("Product not found"));

        var response = orderController.placeOrder(orderDTO, new UserPrincipal(1L, "testuser", "password", "user"));

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Product not found", response.getBody());
        verify(orderService, times(1)).createOrder(1L, 2, 1L);
    }

    @Test
    void testDeleteOrder() {
        doNothing().when(orderService).deleteOrder(1L);

        var response = orderController.deleteOrder(1L);

        assertEquals("Order deleted successfully.", response.getBody());
        verify(orderService, times(1)).deleteOrder(1L);
    }

    @Test
    void testGetAllOrders() {
        List<Order> orders = List.of(new Order());
        when(orderService.getAllOrders()).thenReturn(orders);

        var response = orderController.getAllOrders();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(orders, response.getBody());
        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void testUpdateOrderStatusSuccess() {
        Long orderId = 1L;
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setStatus("shipped");

        doNothing().when(orderService).updateOrderStatus(orderId, "shipped");

        var response = orderController.updateOrderStatus(orderId, orderDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Order status updated successfully.", response.getBody());
        verify(orderService, times(1)).updateOrderStatus(orderId, "shipped");
    }

    @Test
    void testUpdateOrderStatusFailure() {
        Long orderId = 1L;
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setStatus("invalid-status");

        doThrow(new IllegalArgumentException("Invalid order status"))
                .when(orderService).updateOrderStatus(orderId, "invalid-status");

        var response = orderController.updateOrderStatus(orderId, orderDTO);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid order status", response.getBody());
        verify(orderService, times(1)).updateOrderStatus(orderId, "invalid-status");
    }

}
