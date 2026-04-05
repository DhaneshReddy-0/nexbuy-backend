package com.nexbuy.backend.controller;

import com.nexbuy.backend.model.Product;
import com.nexbuy.backend.model.Review;
import com.nexbuy.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired private ProductService productService;

    @GetMapping
    public List<Product> getAll() { return productService.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return productService.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product create(@RequestBody Product p) { return productService.save(p); }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Product p) {
        Product updated = productService.update(id, p);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable Long id) {
        productService.delete(id);
        return Map.of("message", "Product deleted");
    }

    @GetMapping("/category/{cat}")
    public List<Product> byCategory(@PathVariable String cat) { return productService.getByCategory(cat); }

    @GetMapping("/nexplus")
    public List<Product> nexPlus() { return productService.getNexPlusProducts(); }

    @GetMapping("/under/{price}")
    public List<Product> underPrice(@PathVariable double price) { return productService.getUnderPrice(price); }

    @GetMapping("/top-rated")
    public List<Product> topRated() { return productService.getTopRated(); }

    @GetMapping("/deals")
    public List<Product> deals() { return productService.getDeals(); }

    @GetMapping("/trending")
    public List<Product> trending() { return productService.getTrending(); }

    @GetMapping("/{id}/similar")
    public List<Product> similar(@PathVariable Long id, @RequestParam String category) {
        return productService.getSimilar(category, id);
    }

    @GetMapping("/price-range")
    public List<Product> priceRange(@RequestParam double min, @RequestParam double max) {
        return productService.getPriceRange(min, max);
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String q) { return productService.search(q); }

    @PostMapping("/{id}/reviews")
    public ResponseEntity<?> addReview(@PathVariable Long id, @RequestBody Review review) {
        Product p = productService.addReview(id, review);
        return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }
}
