package perfiltic.ecommerce.api.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import perfiltic.ecommerce.api.dto.model.CategoryDto;
import perfiltic.ecommerce.api.dto.response.Response;
import perfiltic.ecommerce.api.service.api.CategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list/{id}")
    public Response getAllCategories(@PathVariable("id") Long id) {
        return Response.ok().setPayload(categoryService.getAllCategories(id));
    }

    @GetMapping(value = "/{id}")
    public Response getCategoryById(@PathVariable("id") Long id) {
        return Response.ok().setPayload(categoryService.getCategoryById(id));
    }

    @PostMapping("/add")
    public Response addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return Response.ok().setPayload(categoryService.addCategory(categoryDto));
    }

    @PostMapping("/update")
    public Response updateCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return Response.ok().setPayload(categoryService.updateCategory(categoryDto));
    }
}
