package br.barbosa.oakdev.exceptions;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import br.barbosa.oakdev.controllers.ProductController;
import br.barbosa.oakdev.services.ProductService;

import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private GlobalExceptionHandler globalExceptionHandler;

  @Mock
  private ProductService productService;

  @InjectMocks
  private ProductController productController;

  @BeforeEach
  void setUp() {
    globalExceptionHandler = new GlobalExceptionHandler();
    mockMvc = standaloneSetup(globalExceptionHandler).build();
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void handleResponseStatusException_ShouldReturnCustomErrorResponse() throws Exception {
    // Arrange
    Long invalidId = 333L;

    when(productController.getProductById(invalidId))
        .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

    // Act & Assert
    mockMvc.perform(get("/api/products/999"))
        .andExpect(status().isNotFound()) // desisto por enqto
        .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
        .andExpect(jsonPath("$.message").value("Product not found"));
  }

  @SuppressWarnings("unchecked")
  @Test
  void handleValidationExceptions_ShouldReturn400() throws Exception {
    // Arrange
    BindingResult bindingResult = Mockito.mock(BindingResult.class);
    FieldError fieldError = new FieldError("product", "name", "Name is required");

    when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(fieldError));

    MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

    // Act
    ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleValidationExceptions(exception);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Validation failed", response.getBody().get("message"));
    assertEquals("Name is required", ((Map<String, String>) response.getBody().get("errors")).get("name"));
  }

  @Test
  void handleGenericException_ShouldReturn500() throws Exception {
    // Arrange
    Exception exception = new Exception("An error occurred");

    // Act
    ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = globalExceptionHandler
        .handleGenericException(exception);

    // Assert
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("An unexpected error occurred.", response.getBody().getMessage());
  }
}