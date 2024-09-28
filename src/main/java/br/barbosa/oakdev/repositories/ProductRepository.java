package br.barbosa.oakdev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.barbosa.oakdev.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
