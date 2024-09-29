package br.barbosa.oakdev.services;

import java.util.List;
import br.barbosa.oakdev.entities.Product;

public interface ProductService {

  /**
   * Retorna todos os produtos disponíveis, ordenados pelo preço.
   *
   * @return lista de produtos
   */
  List<Product> findAll();

  /**
   * Salva um novo produto ou atualiza um produto existente.
   *
   * @param product o produto a ser salvo
   * @return o produto salvo
   */
  Product save(Product product);

  /**
   * Exclui um produto pelo ID.
   *
   * @param id ID do produto a ser excluído
   */
  void deleteById(Long id);

  /**
   * Busca um produto pelo ID.
   *
   * @param id ID do produto a ser buscado
   * @return o produto encontrado
   */
  Product findById(Long id);
}
