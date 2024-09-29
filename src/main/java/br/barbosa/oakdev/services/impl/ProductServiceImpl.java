package br.barbosa.oakdev.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import br.barbosa.oakdev.entities.Product;
import br.barbosa.oakdev.repositories.ProductRepository;
import br.barbosa.oakdev.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository productRepository;

  /**
   * Retorna todos os produtos disponíveis, ordenados pelo preço.
   *
   * @return lista de produtos
   */
  public List<Product> findAll() {
    return productRepository.findAll(Sort.by("price").ascending());
  }

  /**
   * Salva um novo produto ou atualiza um produto existente.
   *
   * @param product o produto a ser salvo
   * @return o produto salvo
   */
  public Product save(Product product) {
    return productRepository.save(product);
  }

  /**
   * Exclui um produto pelo ID.
   * Se o produto não for encontrado, uma exceção ResponseStatusException é
   * lançada.
   *
   * @param id ID do produto a ser excluído
   */
  public void deleteById(Long id) {
    // Verifica se o produto existe antes de tentar excluí-lo
    if (!productRepository.existsById(id)) {
      // Lança uma exceção com status 404 se o produto não for encontrado
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }
    // Exclui o produto do repositório
    productRepository.deleteById(id);
  }

  /**
   * Busca um produto pelo ID.
   * Se o produto não for encontrado, uma exceção ResponseStatusException é
   * lançada.
   *
   * @param id ID do produto a ser buscado
   * @return o produto encontrado
   */
  public Product findById(Long id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
  }

}