package perfiltic.ecommerce.api.service.api;

import perfiltic.ecommerce.api.dto.model.CategoryDto;
import perfiltic.ecommerce.api.dto.model.ProductDto;
import perfiltic.ecommerce.api.dto.model.ProductPhotoDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();

    CategoryDto getCategoryById(Long id);

    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto);

}
