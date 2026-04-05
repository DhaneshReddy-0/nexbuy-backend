package com.nexbuy.backend.repository;

import com.nexbuy.backend.model.PriceAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PriceAlertRepository extends JpaRepository<PriceAlert, Long> {
    List<PriceAlert> findByUserId(Long userId);
    List<PriceAlert> findByProductIdAndTriggeredFalse(Long productId);
    List<PriceAlert> findByUserIdAndTriggeredFalse(Long userId);
}
