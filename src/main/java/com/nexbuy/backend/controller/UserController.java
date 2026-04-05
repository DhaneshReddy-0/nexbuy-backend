package com.nexbuy.backend.controller;

import com.nexbuy.backend.model.User;
import com.nexbuy.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired private UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfile(@PathVariable Long id) {
        return userRepository.findById(id)
            .map(u -> { u.setPassword(null); return ResponseEntity.ok(u); })
            .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/wallet/topup")
    public ResponseEntity<?> topUpWallet(@PathVariable Long id, @RequestParam double amount) {
        return userRepository.findById(id).map(u -> {
            u.setWalletBalance(u.getWalletBalance() + amount);
            userRepository.save(u);
            return ResponseEntity.ok(Map.of("walletBalance", u.getWalletBalance()));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/membership")
    public ResponseEntity<?> upgradeMembership(@PathVariable Long id, @RequestParam String tier) {
        return userRepository.findById(id).map(u -> {
            u.setMembershipTier(tier);
            if ("PLUS".equals(tier)) u.setWalletBalance(u.getWalletBalance() + 200); // bonus
            userRepository.save(u);
            return ResponseEntity.ok(Map.of("tier", tier));
        }).orElse(ResponseEntity.notFound().build());
    }
}
