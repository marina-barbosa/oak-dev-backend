// package br.barbosa.oakdev.controllers;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import br.barbosa.oakdev.dtos.ProductDTO;
// import br.barbosa.oakdev.services.ProductServiceWithDTO;

// @RestController
// @RequestMapping("/v2/products")
// // @CrossOrigin(origins = "http://localhost:5173")
// @CrossOrigin(origins = "*")
// public class ProductControllerWithDTO {

// @Autowired
// private ProductServiceWithDTO productService;

// /**
// * Retorna todos os produtos.
// *
// * @return lista de produtos
// */
// @GetMapping
// public ResponseEntity<List<ProductDTO>> getAllProducts() {
// List<ProductDTO> products = productService.findAll();
// return ResponseEntity.ok(products);
// }

// /**
// * Busca um produto pelo ID.
// *
// * @param id ID do produto a ser buscado
// * @return produto encontrado
// */
// @GetMapping("/{id}")
// public ResponseEntity<ProductDTO> getOneProduct(@PathVariable Long id) {
// ProductDTO product = productService.findById(id);
// return ResponseEntity.ok(product);
// }

// /**
// * Cria um novo produto.
// *
// * @param productDTO dados do produto a ser criado
// * @return produto criado
// */
// @PostMapping
// public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO
// productDTO) {
// ProductDTO createdProduct = productService.save(productDTO);
// return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
// }

// /**
// * Atualiza um produto existente.
// *
// * @param id ID do produto a ser atualizado
// * @param productDTO dados atualizados do produto
// * @return produto atualizado
// */
// @PutMapping("/{id}")
// public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,
// @RequestBody ProductDTO productDTO) {
// ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
// return ResponseEntity.ok(updatedProduct);
// }

// /**
// * Exclui um produto pelo ID.
// *
// * @param id ID do produto a ser excluído
// * @return resposta HTTP sem conteúdo
// */
// @DeleteMapping("/{id}")
// public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
// productService.deleteById(id);
// return ResponseEntity.noContent().build();
// }
// }
