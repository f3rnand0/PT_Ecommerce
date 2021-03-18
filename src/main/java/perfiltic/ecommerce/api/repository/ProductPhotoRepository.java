package perfiltic.ecommerce.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import perfiltic.ecommerce.api.model.ProductPhoto;

import java.util.List;

public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Long> {

    @Query("select p from ProductPhoto p join fetch p.product where p.product.id = :id")
    List<ProductPhoto> findByProductId(@Param("id") Long productId);
}
