package perfiltic.ecommerce.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import perfiltic.ecommerce.api.model.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c left join fetch c.category where c.category.id is null")
    List<Category> findAllParents();

    @Query("select c from Category c where c.category.id = :id")
    List<Category> findAllChildren(@Param("id") Long id);

    @Query("select c from Category c where c.name = :name and c.category.id = :categoryId")
    Category findByName(@Param("name") String name, @Param("categoryId") Long categoryId);
}
