package com.example.cart.model;

import com.example.cart.DTO.CartItem;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    private String id;
    private List<CartItem> items = new ArrayList<>();

    public Cart(String cartId) {
        id=cartId;
    }
}
