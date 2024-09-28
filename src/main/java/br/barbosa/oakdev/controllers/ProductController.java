package br.barbosa.oakdev.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.barbosa.oakdev.entities.Product;
import br.barbosa.oakdev.services.ProductService;

@RestController
@RequestMapping("/api/products")
// @CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "*")
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping
  public List<Product> getAllProducts() {
    return productService.findAll();
  }

  @PostMapping
  public Product createProduct(@RequestBody Product product) {
    return productService.save(product);
  }

  @PutMapping("/{id}")
  public Product updateProduct(@PathVariable Long id, @RequestBody Product productAtualizado) {
    Product product = productService.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    product.setName(productAtualizado.getName());
    product.setDescription(productAtualizado.getDescription());
    product.setPrice(productAtualizado.getPrice());
    product.setAvailableForSale(productAtualizado.getAvailableForSale());
    return productService.save(product);
  }

  @DeleteMapping("/{id}")
  public void deleteProduct(@PathVariable Long id) {
    productService.deleteById(id);
  }
}
