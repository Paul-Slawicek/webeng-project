package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.ProductDTO;
import at.technikum.springrestbackend.entity.Product;
import at.technikum.springrestbackend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(ProductDTO productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());
        product.setDescription(productDto.getDescription());
        product.setPicture(productDto.getPicture());
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

}
