package com.example.cart.service;

import com.example.cart.client.ProductServiceClient;
import com.example.cart.model.Cart;
import com.example.cart.DTO.CartItem;
import com.example.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.cart.DTO.ProductDTO;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductServiceClient productServiceClient;

    // Add an item to the cart
    public Cart addItemToCart(String cartId, String productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElse(new Cart(cartId));

        // Retrieve product details from ProductService
        ProductDTO product = productServiceClient.getProductById(productId);

        // Check product availability
        if (product.getCount() < quantity) {
            throw new RuntimeException("Product stock is insufficient.");
        }

        // Add or update the cart item
        CartItem cartItem = new CartItem();
        cartItem.setProductId(product.getId());
        cartItem.setQuantity(quantity);
        cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));

        cart.getItems().add(cartItem);
        return cartRepository.save(cart);
    }

    // Remove an item from the cart
    public Cart removeItemFromCart(String cartId, String productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        return cartRepository.save(cart);
    }
}
