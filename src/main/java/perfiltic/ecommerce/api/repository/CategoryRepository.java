package perfiltic.ecommerce.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import perfiltic.ecommerce.api.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
