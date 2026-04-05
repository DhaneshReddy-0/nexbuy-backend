package com.nexbuy.backend.repository;

import com.nexbuy.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByCustomerEmail(String email);
    List<Order> findByStatus(String status);
    Optional<Order> findByTrackingId(String trackingId);
}
