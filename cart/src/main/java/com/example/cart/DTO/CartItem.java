package com.example.cart.DTO;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {
    private String productId;
    private int quantity;
    private BigDecimal price;
}
