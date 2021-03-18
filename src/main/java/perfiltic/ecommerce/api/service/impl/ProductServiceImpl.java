package perfiltic.ecommerce.api.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import perfiltic.ecommerce.api.currency.consumer.CurrencyQuotes;
import perfiltic.ecommerce.api.dto.model.ProductDto;
import perfiltic.ecommerce.api.dto.model.ProductPhotoDto;
import perfiltic.ecommerce.api.model.Category;
import perfiltic.ecommerce.api.model.Product;
import perfiltic.ecommerce.api.model.ProductPhoto;
import perfiltic.ecommerce.api.repository.CategoryRepository;
import perfiltic.ecommerce.api.repository.ProductPhotoRepository;
import perfiltic.ecommerce.api.repository.ProductRepository;
import perfiltic.ecommerce.api.service.api.ProductService;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPhotoRepository productPhotoRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProductDto> getAllProducts() {
        List<ProductDto> productDtoList = productRepository.findAll().stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
        productDtoList
                .forEach(productDto -> productDto.setPriceConverted(convertUSDTOCOP(productDto.getPrice())));
        return productDtoList;
    }

    @Override
    public ProductDto getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            ProductDto productDto = modelMapper.map(product.get(), ProductDto.class);
            productDto.setPriceConverted(convertUSDTOCOP(productDto.getPrice()));
            return productDto;
        }
        throw new EntityNotFoundException("Product not found: " + id);
    }

    private BigDecimal convertUSDTOCOP(BigDecimal price) {
        CurrencyQuotes currencyQuotes = new CurrencyQuotes();
        currencyQuotes.convertUSDToCOP();
        return price.multiply(new BigDecimal(currencyQuotes.quotes.getUSDCOP()));
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        Optional<Product> productToInsert =
                Optional.ofNullable(productRepository.findByName(productDto.getName()));
        if (productToInsert.isEmpty()) {
            Optional<Category> category = categoryRepository.findById(productDto.getCategoryId());
            if (category.isPresent()) {
                Product product = new Product()
                        .setCategory(category.get())
                        .setName(productDto.getName())
                        .setDescription(productDto.getDescription())
                        .setWeight(productDto.getWeight())
                        .setPrice(productDto.getPrice());
                productRepository.save(product);
                return modelMapper.map(product, ProductDto.class);
            } else
                throw new EntityNotFoundException("Category not found: " + productDto.getCategoryId());
        }
        throw new DataIntegrityViolationException("Product already exists: " + productDto.getName());
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        Optional<Product> product = Optional.ofNullable(productRepository.findByName(productDto.getName()));
        if (product.isPresent()) {
            Optional<Category> category = categoryRepository.findById(productDto.getCategoryId());
            if (category.isPresent()) {
                Product newProduct = new Product()
                        .setId(product.get().getId())
                        .setCategory(category.get())
                        .setName(productDto.getName())
                        .setDescription(productDto.getDescription())
                        .setWeight(productDto.getWeight())
                        .setPrice(productDto.getPrice());

                productRepository.save(newProduct);
                return modelMapper.map(newProduct, ProductDto.class);
            } else
                throw new EntityNotFoundException("Category not found: " + productDto.getCategoryId());
        }
        throw new EntityNotFoundException("Product not found: " + productDto.getName());
    }

    @Override
    public List<ProductPhotoDto> getPhotosByProductId(Long productId) {
        return productPhotoRepository.findByProductId(productId).stream()
                .map(productPhoto -> modelMapper.map(productPhoto, ProductPhotoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductPhotoDto addProductPhoto(ProductPhotoDto productPhotoDto) {
        Optional<Product> product = productRepository.findById(productPhotoDto.getProductId());
        if (product.isPresent()) {
            ProductPhoto productPhoto = new ProductPhoto()
                    .setProduct(product.get())
                    .setPhoto(productPhotoDto.getPhoto());
            productPhotoRepository.save(productPhoto);
            return modelMapper.map(productPhoto, ProductPhotoDto.class);
        }
        throw new EntityNotFoundException("Product not found: " + productPhotoDto.getProductId());
    }

    @Override
    public ProductPhotoDto updateProductPhoto(ProductPhotoDto productPhotoDto) {
        Optional<Product> product = productRepository.findById(productPhotoDto.getProductId());
        if (product.isPresent()) {
            Optional<ProductPhoto> productPhoto =
                    productPhotoRepository.findById(productPhotoDto.getId());
            if (productPhoto.isPresent()) {
                ProductPhoto newProductPhoto = new ProductPhoto()
                        .setId(productPhoto.get().getId())
                        .setProduct(product.get())
                        .setPhoto(productPhotoDto.getPhoto());
                productPhotoRepository.save(newProductPhoto);
                return modelMapper.map(newProductPhoto, ProductPhotoDto.class);
            } else
                throw new EntityNotFoundException("Product photo not found: " + productPhotoDto.getId());
        }
        throw new EntityNotFoundException("Product not found: " + productPhotoDto.getProductId());
    }

    @Override
    public List<ProductDto> getProductsByCategoryId(Long categoryId) {
        List<ProductDto> productDtoList = productRepository.findByCategoryId(categoryId).stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
        productDtoList
                .forEach(productDto -> productDto.setPriceConverted(convertUSDTOCOP(productDto.getPrice())));
        return productDtoList;
    }
}
