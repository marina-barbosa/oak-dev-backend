package br.barbosa.oakdev.controllers;

import br.barbosa.oakdev.entities.Product;
import br.barbosa.oakdev.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

class ProductControllerTest {

  private MockMvc mockMvc;

  @Mock
  private ProductService productService;

  @InjectMocks
  private ProductController productController;

  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    this.objectMapper = new ObjectMapper();
  }

  @Test
  void getAllProducts_ShouldReturnProductList() throws Exception {
    // Arrange
    Product product1 = new Product(1L, "Product 1", "Description 1", 10.0, true);
    Product product2 = new Product(2L, "Product 2", "Description 2", 20.0, true);
    List<Product> products = Arrays.asList(product1, product2);

    when(productService.findAll()).thenReturn(products);

    // Act & Assert
    mockMvc.perform(get("/api/products"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value("Product 1"))
        .andExpect(jsonPath("$[1].name").value("Product 2"));

    verify(productService, times(1)).findAll();
  }

  @Test
  void getOneProduct_ShouldReturnProduct() throws Exception {
    // Arrange
    Product product = new Product(1L, "Product 1", "Description 1", 10.0, true);
    when(productService.findById(anyLong())).thenReturn(product);

    // Act & Assert
    mockMvc.perform(get("/api/products/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Product 1"))
        .andExpect(jsonPath("$.description").value("Description 1"));

    verify(productService, times(1)).findById(anyLong());
  }

  @Test
  void createProduct_ShouldReturnCreatedProduct() throws Exception {
    // Arrange
    Product product = new Product(1L, "New Product", "New Description", 30.0, true);
    when(productService.save(any(Product.class))).thenReturn(product);

    // Act & Assert
    mockMvc.perform(post("/api/products")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(product)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("New Product"))
        .andExpect(jsonPath("$.description").value("New Description"));

    verify(productService, times(1)).save(any(Product.class));
  }

  @Test
  void updateProduct_ShouldReturnUpdatedProduct() throws Exception {
    // Arrange
    Product existingProduct = new Product(1L, "Existing Product", "Existing Description", 30.0, true);
    Product updatedProduct = new Product(1L, "Updated Product", "Updated Description", 50.0, true);

    when(productService.findById(anyLong())).thenReturn(existingProduct);
    when(productService.save(any(Product.class))).thenReturn(updatedProduct);

    // Act & Assert
    mockMvc.perform(put("/api/products/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updatedProduct)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Updated Product"))
        .andExpect(jsonPath("$.description").value("Updated Description"));

    verify(productService, times(1)).findById(anyLong());
    verify(productService, times(1)).save(any(Product.class));
  }

  @Test
  void deleteProduct_ShouldReturnNoContent() throws Exception {
    // Arrange
    doNothing().when(productService).deleteById(anyLong());

    // Act & Assert
    mockMvc.perform(delete("/api/products/1"))
        .andExpect(status().isOk());

    verify(productService, times(1)).deleteById(anyLong());
  }

  @Test
  void getOneProduct_NotFound_ShouldReturn404() throws Exception {
    // Arrange
    when(productService.findById(anyLong()))
        .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

    // Act & Assert
    mockMvc.perform(get("/api/products/999"))
        .andExpect(status().isNotFound());

    verify(productService, times(1)).findById(anyLong());
  }
}
