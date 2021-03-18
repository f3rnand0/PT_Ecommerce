package perfiltic.ecommerce.api.service.api;

import perfiltic.ecommerce.api.dto.model.ProductDto;
import perfiltic.ecommerce.api.dto.model.ProductPhotoDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();

    ProductDto getProductById(Long id);

    ProductDto addProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto);

    List<ProductPhotoDto> getPhotosByProductId(Long productId);

    ProductPhotoDto addProductPhoto(ProductPhotoDto productPhotoDto);

    ProductPhotoDto updateProductPhoto(ProductPhotoDto productPhotoDto);

    List<ProductDto> getProductsByCategoryId(Long categoryId);
}
