package com.nexbuy.backend.service;

import com.nexbuy.backend.model.PriceAlert;
import com.nexbuy.backend.model.WishlistItem;
import com.nexbuy.backend.repository.PriceAlertRepository;
import com.nexbuy.backend.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WishlistService {
    @Autowired private WishlistRepository wishlistRepository;

    public List<WishlistItem> getByUser(Long userId) { return wishlistRepository.findByUserId(userId); }

    public WishlistItem add(WishlistItem item) {
        if (wishlistRepository.existsByUserIdAndProductId(item.getUserId(), item.getProductId())) {
            return wishlistRepository.findByUserIdAndProductId(item.getUserId(), item.getProductId()).orElse(item);
        }
        return wishlistRepository.save(item);
    }

    @Transactional
    public void remove(Long userId, Long productId) {
        wishlistRepository.deleteByUserIdAndProductId(userId, productId);
    }

    public boolean isWishlisted(Long userId, Long productId) {
        return wishlistRepository.existsByUserIdAndProductId(userId, productId);
    }
}
