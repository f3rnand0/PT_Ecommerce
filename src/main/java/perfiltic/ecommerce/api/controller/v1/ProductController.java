package perfiltic.ecommerce.api.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import perfiltic.ecommerce.api.dto.model.ProductDto;
import perfiltic.ecommerce.api.dto.model.ProductPhotoDto;
import perfiltic.ecommerce.api.dto.response.Response;
import perfiltic.ecommerce.api.service.api.ProductService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public Response getAllProducts() {
        return Response.ok().setPayload(productService.getAllProducts());
    }

    @GetMapping(value = "/{id}")
    public Response getProductById(@PathVariable("id") Long id) {
        return Response.ok().setPayload(productService.getProductById(id));
    }

    @PostMapping("/add")
    public Response addProduct(@Valid @RequestBody ProductDto productDto) {
        return Response.ok().setPayload(productService.addProduct(productDto));
    }

    @PostMapping("/update")
    public Response updateProduct(@Valid @RequestBody ProductDto productDto) {
        return Response.ok().setPayload(productService.updateProduct(productDto));
    }

    @GetMapping(value = "/photos/{productId}")
    public Response getPhotosByProductId(@PathVariable("productId") Long productId) {
        return Response.ok().setPayload(productService.getPhotosByProductId(productId));
    }

    @PostMapping("/addPhoto")
    public Response addProductPhoto(@Valid @RequestBody ProductPhotoDto productPhotoDto) {
        return Response.ok().setPayload(productService.addProductPhoto(productPhotoDto));
    }

    @PostMapping("/updatePhoto")
    public Response updateProductPhoto(@Valid @RequestBody ProductPhotoDto productPhotoDto) {
        return Response.ok().setPayload(productService.updateProductPhoto(productPhotoDto));
    }

    @GetMapping(value = "/category/{categoryId}")
    public Response getProductsByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return Response.ok().setPayload(productService.getProductsByCategoryId(categoryId));
    }

}
