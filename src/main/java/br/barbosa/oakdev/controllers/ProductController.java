package br.barbosa.oakdev.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.barbosa.oakdev.entities.Product;
import br.barbosa.oakdev.services.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
// @CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "*")
public class ProductController {

  @Autowired
  private ProductService productService;

  /**
   * Retorna todos os produtos disponíveis.
   *
   * @return lista de produtos
   */
  @GetMapping
  public List<Product> getAllProducts() {
    return productService.findAll();
  }

  /**
   * Busca um produto pelo ID.
   * Se o produto não for encontrado, uma exceção ResponseStatusException é
   * lançada.
   *
   * @param id ID do produto a ser buscado
   * @return o produto encontrado
   */
  @GetMapping("/{id}")
  public Product getProductById(@PathVariable Long id) {
    return productService.findById(id);
  }

  /**
   * Cria um novo produto.
   *
   * @param product objeto do produto a ser criado
   * @return o produto criado
   */
  @PostMapping
  public Product createProduct(@Valid @RequestBody Product product) {
    return productService.save(product);
  }

  /**
   * Atualiza um produto existente.
   * Se o produto não for encontrado, uma exceção ResponseStatusException é
   * lançada.
   *
   * @param id                ID do produto a ser atualizado
   * @param productAtualizado objeto do produto com as informações atualizadas
   * @return o produto atualizado
   */
  @PutMapping("/{id}")
  public Product updateProduct(@PathVariable Long id, @RequestBody Product productAtualizado) {
    // Busca o produto pelo ID
    Product product = productService.findById(id);
    // Atualiza as informações do produto
    product.setName(productAtualizado.getName());
    product.setDescription(productAtualizado.getDescription());
    product.setPrice(productAtualizado.getPrice());
    product.setAvailableForSale(productAtualizado.getAvailableForSale());
    return productService.save(product);
  }

  /**
   * Exclui um produto pelo ID.
   * Se o produto não for encontrado, uma exceção ResponseStatusException é
   * lançada.
   *
   * @param id ID do produto a ser excluído
   */
  @DeleteMapping("/{id}")
  public void deleteProduct(@PathVariable Long id) {
    // Chama o método de deleteById, que verifica se o produto existe
    productService.deleteById(id);
  }
}
