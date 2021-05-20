package perfiltic.ecommerce.api.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import perfiltic.ecommerce.api.dto.model.CategoryDto;
import perfiltic.ecommerce.api.model.Category;
import perfiltic.ecommerce.api.repository.CategoryRepository;
import perfiltic.ecommerce.api.service.api.CategoryService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CategoryDto> getAllCategories(Long id) {
        if (id == 0)
            return categoryRepository.findAllParents().stream()
                    .map(category -> modelMapper.map(category, CategoryDto.class))
                    .collect(Collectors.toList());
        else
            return categoryRepository.findAllChildren(id).stream()
                    .map(category -> modelMapper.map(category, CategoryDto.class))
                    .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return modelMapper.map(category.get(), CategoryDto.class);
        }
        throw new EntityNotFoundException("Category not found: " + id);
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Optional<Category> categoryToInsert =
                Optional.ofNullable(categoryRepository.findByName(categoryDto.getName(),
                                                                  categoryDto.getCategory().getId()));
        if (categoryToInsert.isEmpty()) {
            Category category = new Category()
                    .setName(categoryDto.getName())
                    .setPhoto(categoryDto.getPhoto());
            if (categoryDto.getCategory() != null && categoryDto.getCategory().getId() != null)
                category.setCategory(modelMapper.map(categoryDto.getCategory(), Category.class));
            categoryRepository.save(category);
            return modelMapper.map(category, CategoryDto.class);
        }
        throw new DataIntegrityViolationException(
                "Category already exists: name: " + categoryDto.getName() + ", parentid: " +
                categoryDto.getCategory().getId());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Optional<Category> category =
                Optional.ofNullable(categoryRepository.findByName(categoryDto.getName(),
                                                                  categoryDto.getCategory().getId()));
        if (category.isPresent()) {
            Category newCategory = new Category()
                    .setId(category.get().getId())
                    .setName(categoryDto.getName())
                    .setPhoto(categoryDto.getPhoto());
            if (categoryDto.getCategory() != null && categoryDto.getCategory().getId() != null)
                newCategory.setCategory(modelMapper.map(categoryDto.getCategory(), Category.class));

            categoryRepository.save(newCategory);
            return modelMapper.map(newCategory, CategoryDto.class);
        }
        throw new EntityNotFoundException(
                "Category not found: name: " + categoryDto.getName() + ", parentid: " +
                categoryDto.getCategory().getId());
    }

}
