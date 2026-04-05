package com.nexbuy.backend.service;

import com.nexbuy.backend.model.PriceAlert;
import com.nexbuy.backend.repository.PriceAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PriceAlertService {
    @Autowired private PriceAlertRepository alertRepository;

    public PriceAlert create(PriceAlert alert) { return alertRepository.save(alert); }

    public List<PriceAlert> getByUser(Long userId) { return alertRepository.findByUserId(userId); }

    public List<PriceAlert> getActiveByUser(Long userId) { return alertRepository.findByUserIdAndTriggeredFalse(userId); }

    public void delete(Long id) { alertRepository.deleteById(id); }
}
