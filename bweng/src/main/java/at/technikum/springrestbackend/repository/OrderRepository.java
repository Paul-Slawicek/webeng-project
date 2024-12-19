package at.technikum.springrestbackend.repository;


import at.technikum.springrestbackend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
