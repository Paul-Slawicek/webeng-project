package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.ProductDTO;
import at.technikum.springrestbackend.entity.Product;
import at.technikum.springrestbackend.service.FileService;
import at.technikum.springrestbackend.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final FileService fileService;

    public ProductController(ProductService productService, FileService fileService) {
        this.productService = productService;
        this.fileService = fileService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> addProduct(
            @RequestPart("product") String productJson,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            logger.info("Received product JSON: {}", productJson);
            if (file != null && !file.isEmpty()) {
                logger.info("Received file: {}", file.getOriginalFilename());
            } else {
                logger.warn("No file uploaded");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            ProductDTO productDto = objectMapper.readValue(productJson, ProductDTO.class);

            String fileReference = null;
            if (file != null && !file.isEmpty()) {
                fileReference = fileService.upload(file);
                logger.info("Hochgeladene Datei: " + fileReference);
            }
            logger.info("Hochgeladene Datei: " + fileReference);
            productDto.setPicture(fileReference);

            Product product = productService.createProduct(productDto);

            return ResponseEntity.ok(product);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/loadAll")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

}
