package com.nexbuy.backend.controller;

import com.nexbuy.backend.model.WishlistItem;
import com.nexbuy.backend.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired private WishlistService wishlistService;

    @GetMapping("/user/{userId}")
    public List<WishlistItem> get(@PathVariable Long userId) { return wishlistService.getByUser(userId); }

    @PostMapping
    public WishlistItem add(@RequestBody WishlistItem item) { return wishlistService.add(item); }

    @DeleteMapping("/user/{userId}/product/{productId}")
    public Map<String, String> remove(@PathVariable Long userId, @PathVariable Long productId) {
        wishlistService.remove(userId, productId);
        return Map.of("message", "Removed from wishlist");
    }
}
