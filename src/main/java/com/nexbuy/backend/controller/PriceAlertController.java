package com.nexbuy.backend.controller;

import com.nexbuy.backend.model.PriceAlert;
import com.nexbuy.backend.service.PriceAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alerts")
public class PriceAlertController {

    @Autowired private PriceAlertService alertService;

    @GetMapping("/user/{userId}")
    public List<PriceAlert> get(@PathVariable Long userId) { return alertService.getByUser(userId); }

    @GetMapping("/user/{userId}/active")
    public List<PriceAlert> getActive(@PathVariable Long userId) { return alertService.getActiveByUser(userId); }

    @PostMapping
    public PriceAlert create(@RequestBody PriceAlert alert) { return alertService.create(alert); }

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable Long id) {
        alertService.delete(id);
        return Map.of("message", "Alert deleted");
    }
}
