package com.example.cart.controller;

import com.example.cart.model.Cart;
import com.example.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/{cartId}/items")
    public ResponseEntity<Cart> addItemToCart(
            @PathVariable String cartId,
            @RequestParam String productId,
            @RequestParam int quantity) {
        Cart updatedCart = cartService.addItemToCart(cartId, productId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<Cart> removeItemFromCart(
            @PathVariable String cartId,
            @PathVariable String productId) {
        Cart updatedCart = cartService.removeItemFromCart(cartId, productId);
        return ResponseEntity.ok(updatedCart);
    }
}
