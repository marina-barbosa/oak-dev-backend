package br.barbosa.oakdev.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.barbosa.oakdev.entities.Product;
import br.barbosa.oakdev.repositories.ProductRepository;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  public List<Product> findAll() {
    return productRepository.findAll(Sort.by("price").ascending());
  }

  public Product save(Product product) {
    return productRepository.save(product);
  }

  public void deleteById(Long id) {
    productRepository.deleteById(id);
  }

  public Optional<Product> findById(Long id) {
    return productRepository.findById(id);
  }
}
