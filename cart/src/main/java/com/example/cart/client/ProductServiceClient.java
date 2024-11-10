package com.example.cart.client;

import com.example.cart.DTO.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "product-service", url = "http://localhost:8090/api/products")
@Component
public interface ProductServiceClient {
    @GetMapping("/{id}")
    ProductDTO getProductById(@PathVariable("id") String id);
}