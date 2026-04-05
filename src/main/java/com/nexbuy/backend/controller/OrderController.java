package com.nexbuy.backend.controller;

import com.nexbuy.backend.model.Order;
import com.nexbuy.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired private OrderService orderService;

    @PostMapping
    public Order place(@RequestBody Order order) { return orderService.place(order); }

    @GetMapping
    public List<Order> getAll() { return orderService.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return orderService.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<Order> getByUser(@PathVariable Long userId) { return orderService.getByUserId(userId); }

    @GetMapping("/customer/{email}")
    public List<Order> getByEmail(@PathVariable String email) { return orderService.getByEmail(email); }

    @GetMapping("/track/{trackingId}")
    public ResponseEntity<?> track(@PathVariable String trackingId) {
        return orderService.getByTrackingId(trackingId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam String status) {
        Order updated = orderService.updateStatus(id, status);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/cancel")
    public Map<String, String> cancel(@PathVariable Long id) {
        orderService.cancel(id);
        return Map.of("message", "Order cancelled and refund processed");
    }
}
