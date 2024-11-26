package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.ProductDTO;
import at.technikum.springrestbackend.entity.Product;
import at.technikum.springrestbackend.service.FileService;
import at.technikum.springrestbackend.service.ProductService;
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

    private final ProductService productService;
    private final FileService fileService;

    public ProductController(ProductService productService, FileService fileService) {
        this.productService = productService;
        this.fileService = fileService;
    }



    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addProduct(
            @RequestPart("product") ProductDTO productDto,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            System.out.print("Hello World!");
            String fileReference = null;

            // Upload file if provided
            if (file != null && !file.isEmpty()) {
                fileReference = fileService.upload(file);
            }


            // Set file reference in product DTO
            productDto.setPicture(fileReference);

            // Create the product
            Product product = productService.createProduct(productDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(product);

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
