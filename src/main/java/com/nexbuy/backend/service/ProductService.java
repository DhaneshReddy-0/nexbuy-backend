package com.nexbuy.backend.service;

import com.nexbuy.backend.model.Product;
import com.nexbuy.backend.model.Review;
import com.nexbuy.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired private ProductRepository productRepository;

    public List<Product> getAll() { return productRepository.findAll(); }

    public Optional<Product> getById(Long id) { return productRepository.findById(id); }

    public Product save(Product p) { return productRepository.save(p); }

    public Product update(Long id, Product updated) {
        return productRepository.findById(id).map(p -> {
            p.setName(updated.getName());
            p.setBrand(updated.getBrand());
            p.setDescription(updated.getDescription());
            p.setPrice(updated.getPrice());
            p.setOriginalPrice(updated.getOriginalPrice());
            p.setImageUrl(updated.getImageUrl());
            p.setQuantity(updated.getQuantity());
            p.setCategory(updated.getCategory());
            p.setSubCategory(updated.getSubCategory());
            p.setRating(updated.getRating());
            p.setReviewCount(updated.getReviewCount());
            p.setNexPlus(updated.isNexPlus());
            p.setDealPercent(updated.getDealPercent());
            p.setBadge(updated.getBadge());
            p.setTags(updated.getTags());
            p.setDeliveryDays(updated.getDeliveryDays());
            p.setStockStatus(updated.getStockStatus());
            p.setLoyaltyPointsReward(updated.getLoyaltyPointsReward());
            return productRepository.save(p);
        }).orElse(null);
    }

    public void delete(Long id) { productRepository.deleteById(id); }

    public List<Product> getByCategory(String cat) { return productRepository.findByCategory(cat); }

    public List<Product> getNexPlusProducts() { return productRepository.findByNexPlusTrue(); }

    public List<Product> getUnderPrice(double price) { return productRepository.findByPriceLessThan(price); }

    public List<Product> getTopRated() { return productRepository.findByRatingGreaterThanEqual(4.0); }

    public List<Product> getDeals() { return productRepository.findAllByOrderByDealPercentDesc(); }

    public List<Product> getTrending() { return productRepository.findTrending(); }

    public List<Product> getSimilar(String category, Long id) { return productRepository.findSimilar(category, id); }

    public List<Product> getPriceRange(double min, double max) { return productRepository.findByPriceBetween(min, max); }

    public List<Product> search(String q) {
        return productRepository
            .findByNameContainingIgnoreCaseOrBrandContainingIgnoreCaseOrTagsContainingIgnoreCase(q, q, q);
    }

    public Product addReview(Long productId, Review review) {
        return productRepository.findById(productId).map(p -> {
            p.getReviews().add(review);
            // Recalculate average rating
            double avg = p.getReviews().stream()
                .mapToInt(Review::getRating).average().orElse(p.getRating());
            p.setRating(Math.round(avg * 10.0) / 10.0);
            p.setReviewCount(p.getReviews().size());
            return productRepository.save(p);
        }).orElse(null);
    }
}
