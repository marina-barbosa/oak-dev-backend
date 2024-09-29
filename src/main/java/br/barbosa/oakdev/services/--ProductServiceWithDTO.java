// package br.barbosa.oakdev.services;

// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Sort;
// import org.springframework.http.HttpStatus;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.web.server.ResponseStatusException;

// import br.barbosa.oakdev.dtos.ProductDTO;
// import br.barbosa.oakdev.entities.Product;
// import br.barbosa.oakdev.repositories.ProductRepository;

// @Service
// public class ProductServiceWithDTO {

// @Autowired
// private ProductRepository productRepository;

// private Product convertToEntity(ProductDTO productDTO) {
// Product product = new Product();
// product.setName(productDTO.getName());
// product.setDescription(productDTO.getDescription());
// product.setPrice(productDTO.getPrice());
// product.setAvailableForSale(productDTO.getAvailableForSale());
// return product;
// }

// private ProductDTO convertToDto(Product product) {
// ProductDTO productDTO = new ProductDTO();
// productDTO.setName(product.getName());
// productDTO.setDescription(product.getDescription());
// productDTO.setPrice(product.getPrice());
// productDTO.setAvailableForSale(product.getAvailableForSale());
// return productDTO;
// }

// /**
// * Retorna todos os produtos ordenados por preço.
// *
// * @return lista de produtos
// */
// @Transactional(readOnly = true)
// public List<ProductDTO> findAll() {
// return productRepository.findAll(Sort.by("price").ascending())
// .stream()
// .map(this::convertToDto)
// .collect(Collectors.toList());
// }

// /**
// * Salva um novo produto.
// *
// * @param productDTO dados do produto a ser salvo
// * @return produto salvo
// */
// @Transactional
// public ProductDTO save(ProductDTO productDTO) {
// Product product = convertToEntity(productDTO);
// return convertToDto(productRepository.save(product));
// }

// /**
// * Exclui um produto pelo ID.
// *
// * @param id ID do produto a ser excluído
// */
// @Transactional
// public void deleteById(Long id) {
// if (!productRepository.existsById(id)) {
// throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
// }
// productRepository.deleteById(id);
// }

// /**
// * Busca um produto pelo ID.
// *
// * @param id ID do produto a ser buscado
// * @return produto encontrado
// */
// @Transactional(readOnly = true)
// public ProductDTO findById(Long id) {
// Product product = productRepository.findById(id)
// .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product
// not found"));
// return convertToDto(product);
// }

// /**
// * Atualiza um produto existente.
// *
// * @param id ID do produto a ser atualizado
// * @param productDTO dados atualizados do produto
// * @return produto atualizado
// */
// @Transactional
// public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
// Product existingProduct = productRepository.findById(id)
// .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product
// not found"));

// existingProduct.setName(productDTO.getName());
// existingProduct.setDescription(productDTO.getDescription());
// existingProduct.setPrice(productDTO.getPrice());
// existingProduct.setAvailableForSale(productDTO.getAvailableForSale());

// return convertToDto(productRepository.save(existingProduct));
// }
// }
