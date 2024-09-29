package br.barbosa.oakdev.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import br.barbosa.oakdev.entities.Product;
import br.barbosa.oakdev.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class ProductServiceImplTest {

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductServiceImpl productService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindAll() {
    List<Product> mockProducts = new ArrayList<>();
    Product product1 = new Product();
    product1.setId(1L);
    product1.setName("Produto 1");
    product1.setPrice(100.0);

    Product product2 = new Product();
    product2.setId(2L);
    product2.setName("Produto 2");
    product2.setPrice(200.0);

    mockProducts.add(product1);
    mockProducts.add(product2);

    when(productRepository.findAll(Sort.by("price").ascending())).thenReturn(mockProducts);

    List<Product> result = productService.findAll();

    assertEquals(2, result.size());
    assertEquals("Produto 1", result.get(0).getName());
    assertEquals("Produto 2", result.get(1).getName());
  }

  @Test
  void testSave() {
    Product mockProduct = new Product();
    mockProduct.setId(1L);
    mockProduct.setName("Produto Teste");
    mockProduct.setPrice(150.0);

    when(productRepository.save(mockProduct)).thenReturn(mockProduct);

    Product result = productService.save(mockProduct);

    assertEquals(1L, result.getId());
    assertEquals("Produto Teste", result.getName());
    assertEquals(150.0, result.getPrice());
  }

  @Test
  void testDeleteById_Success() {
    when(productRepository.existsById(1L)).thenReturn(true);

    productService.deleteById(1L);

    verify(productRepository, times(1)).deleteById(1L);
  }

  @Test
  void testDeleteById_NotFound() {
    when(productRepository.existsById(1L)).thenReturn(false);

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      productService.deleteById(1L);
    });

    assertEquals("404 NOT_FOUND \"Product not found\"", exception.getMessage());
  }

  @Test
  void testFindById_Success() {
    Product mockProduct = new Product();
    mockProduct.setId(1L);
    mockProduct.setName("Produto Teste");

    when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));

    Product result = productService.findById(1L);

    assertEquals(1L, result.getId());
    assertEquals("Produto Teste", result.getName());
  }

  @Test
  void testFindById_NotFound() {
    when(productRepository.findById(1L)).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      productService.findById(1L);
    });

    assertEquals("404 NOT_FOUND \"Product not found\"", exception.getMessage());
  }
}