package com.example.product.service;

import com.example.product.DTO.ProductDTO;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // Automatically generates constructor for final fields
public class ProductService {

    private final ProductRepository productRepository;

    // Create a new product from ProductDTO
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCount(productDTO.getCount());  // Set count

        Product savedProduct = productRepository.save(product);

        return new ProductDTO(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getCount()  // Include count in the response
        );
    }

    // Retrieve all products and convert to ProductDTO list
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getCount()  // Include count in the DTO
                ))
                .collect(Collectors.toList());
    }

    // Retrieve a product by ID and convert it to ProductDTO
    public Optional<ProductDTO> getProductById(String id) {
        return productRepository.findById(id)
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getCount()  // Include count in the DTO
                ));
    }

    // Update an existing product from ProductDTO
    public ProductDTO updateProduct(String id, ProductDTO productDTO) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(productDTO.getName());
                    product.setDescription(productDTO.getDescription());
                    product.setPrice(productDTO.getPrice());
                    product.setCount(productDTO.getCount());  // Update count

                    Product updatedProduct = productRepository.save(product);
                    return new ProductDTO(
                            updatedProduct.getId(),
                            updatedProduct.getName(),
                            updatedProduct.getDescription(),
                            updatedProduct.getPrice(),
                            updatedProduct.getCount()  // Include updated count
                    );
                })
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Delete a product by its ID
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
