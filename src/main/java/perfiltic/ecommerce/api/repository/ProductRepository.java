package perfiltic.ecommerce.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import perfiltic.ecommerce.api.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
}
