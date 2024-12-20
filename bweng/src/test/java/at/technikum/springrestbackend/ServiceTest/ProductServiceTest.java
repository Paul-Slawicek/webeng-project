package at.technikum.springrestbackend.ServiceTest;

import at.technikum.springrestbackend.dto.ProductDTO;
import at.technikum.springrestbackend.entity.Product;
import at.technikum.springrestbackend.repository.ProductRepository;
import at.technikum.springrestbackend.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);
    }

    @Test
    void testCreateProduct() {
        ProductDTO productDto = new ProductDTO();
        productDto.setTitle("Test Product");
        productDto.setPrice(19.99);
        productDto.setDescription("Test Description");
        productDto.setCategory("Test Category");
        productDto.setPicture("test.png");

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product product = productService.createProduct(productDto);

        assertNotNull(product);
        assertEquals("Test Product", product.getTitle());
        assertEquals(19.99, product.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }
    @Test
    void testGetAllProducts() {
        // Arrange
        Product product1 = new Product();
        product1.setId(1L);
        product1.setTitle("Product 1");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setTitle("Product 2");

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        // Act
        List<Product> products = productService.getAllProducts();

        // Assert
        assertEquals(2, products.size());
        assertEquals("Product 1", products.get(0).getTitle());
        assertEquals("Product 2", products.get(1).getTitle());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Product 1");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        Optional<Product> result = productService.getProductById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Product 1", result.get().getTitle());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductByIdNotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Product> result = productService.getProductById(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateProduct() {
        // Arrange
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setTitle("Old Product");

        ProductDTO productDTO = new ProductDTO();
        productDTO.setTitle("Updated Product");
        productDTO.setPrice(20.0);
        productDTO.setDescription("Updated Description");
        productDTO.setPicture("updated_picture.png");
        productDTO.setCategory("Updated Category");

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Product updatedProduct = productService.updateProduct(1L, productDTO);

        // Assert
        assertEquals("Updated Product", updatedProduct.getTitle());
        assertEquals(20.0, updatedProduct.getPrice());
        assertEquals("Updated Description", updatedProduct.getDescription());
        assertEquals("updated_picture.png", updatedProduct.getPicture());
        assertEquals("Updated Category", updatedProduct.getCategory());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void testUpdateProductNotFound() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct(1L, productDTO);
        });
        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        Product product = new Product();
        product.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        // Act
        productService.deleteProduct(1L);

        // Assert
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testDeleteProductNotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.deleteProduct(1L);
        });
        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }
}
