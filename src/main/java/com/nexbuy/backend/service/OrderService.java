package com.nexbuy.backend.service;

import com.nexbuy.backend.model.Order;
import com.nexbuy.backend.model.User;
import com.nexbuy.backend.repository.OrderRepository;
import com.nexbuy.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private UserRepository userRepository;

    public Order place(Order order) {
        // Calculate loyalty points earned (1 point per ₹10 spent)
        int pointsEarned = (int) (order.getTotalAmount() / 10);
        order.setPointsEarned(pointsEarned);

        // Set expected delivery
        int days = order.isNexPlusDelivery() ? 1 : 3;
        order.setExpectedDelivery(LocalDateTime.now().plusDays(days));

        Order saved = orderRepository.save(order);

        // Update user wallet, loyalty points after order
        if (order.getUserId() != null) {
            userRepository.findById(order.getUserId()).ifPresent(user -> {
                user.setLoyaltyPoints(user.getLoyaltyPoints() + pointsEarned);
                if (order.getWalletAmountUsed() > 0) {
                    user.setWalletBalance(user.getWalletBalance() - order.getWalletAmountUsed());
                }
                userRepository.save(user);
            });
        }
        return saved;
    }

    public List<Order> getAll() { return orderRepository.findAll(); }

    public Optional<Order> getById(Long id) { return orderRepository.findById(id); }

    public List<Order> getByUserId(Long userId) { return orderRepository.findByUserId(userId); }

    public List<Order> getByEmail(String email) { return orderRepository.findByCustomerEmail(email); }

    public Optional<Order> getByTrackingId(String trackingId) { return orderRepository.findByTrackingId(trackingId); }

    public Order updateStatus(Long id, String status) {
        return orderRepository.findById(id).map(o -> {
            o.setStatus(status);
            return orderRepository.save(o);
        }).orElse(null);
    }

    public void cancel(Long id) {
        orderRepository.findById(id).ifPresent(o -> {
            o.setStatus("CANCELLED");
            // Refund wallet if wallet was used
            if (o.getWalletAmountUsed() > 0 && o.getUserId() != null) {
                userRepository.findById(o.getUserId()).ifPresent(u -> {
                    u.setWalletBalance(u.getWalletBalance() + o.getWalletAmountUsed());
                    userRepository.save(u);
                });
            }
            orderRepository.save(o);
        });
    }
}
