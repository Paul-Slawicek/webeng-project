package at.technikum.springrestbackend.ControllerTest;

import at.technikum.springrestbackend.controller.ProductController;
import at.technikum.springrestbackend.dto.ProductDTO;
import at.technikum.springrestbackend.entity.Product;
import at.technikum.springrestbackend.service.FileService;
import at.technikum.springrestbackend.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Mock
    private FileService fileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProductWithFileUpload() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setTitle("Test Product");
        productDTO.setPrice(19.99);
        productDTO.setPicture("uploaded-file.png");

        Product product = new Product();
        product.setTitle("Test Product");
        product.setPicture("uploaded-file.png");

        MockMultipartFile file = new MockMultipartFile("file", "test-image.png", "image/png", "Dummy Image Content".getBytes());

        when(fileService.upload(file)).thenReturn("uploaded-file.png");
        when(productService.createProduct(any(ProductDTO.class))).thenReturn(product);

        var response = productController.addProduct(
                "{\"title\":\"Test Product\",\"price\":19.99}",
                file
        );

        assertEquals(201, response.getStatusCodeValue());
        verify(fileService, times(1)).upload(file);
        verify(productService, times(1)).createProduct(any(ProductDTO.class));
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = List.of(new Product());
        when(productService.getAllProducts()).thenReturn(products);

        var response = productController.getAllProducts();

        assertEquals(products, response.getBody());
        verify(productService, times(1)).getAllProducts();
    }
    @Test
    void testUpdateProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setTitle("Updated Product");

        Product updatedProduct = new Product();
        updatedProduct.setTitle("Updated Product");

        when(productService.updateProduct(1L, productDTO)).thenReturn(updatedProduct);

        var response = productController.updateProduct(1L, productDTO);

        assertEquals("Updated Product", ((Product) response.getBody()).getTitle());
        verify(productService, times(1)).updateProduct(1L, productDTO);
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productService).deleteProduct(1L);

        var response = productController.deleteProduct(1L);

        assertEquals("Product deleted successfully.", response.getBody());
        verify(productService, times(1)).deleteProduct(1L);
    }
    @Test
    void testGetProductById() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        when(productService.getProductById(productId)).thenReturn(Optional.of(product));

        // Methode aufrufen
        var response = productController.getProductById(productId);

        // Assertions
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    void testGetProductByIdNotFound() {
        Long productId = 1L;
        when(productService.getProductById(productId)).thenReturn(Optional.empty());

        // Methode aufrufen
        var response = productController.getProductById(productId);

        // Assertions
        assertEquals(404, response.getStatusCodeValue());
        verify(productService, times(1)).getProductById(productId);
    }

}
