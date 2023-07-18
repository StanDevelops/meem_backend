package fontys.sem3.its.meem.controller;

import fontys.sem3.its.meem.business.usecase.Category.*;
import fontys.sem3.its.meem.config.security.isauthenticated.IsAuthenticated;
import fontys.sem3.its.meem.domain.request.Category.CreateCategoryRequest;
import fontys.sem3.its.meem.domain.request.Category.UpdateCategoryNameRequest;
import fontys.sem3.its.meem.domain.response.Category.GetCategoriesResponse;
import fontys.sem3.its.meem.domain.response.Category.GetCategoryResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

//done
@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/", allowCredentials = "true")
public class CategoryController {
    //    private final CategoryServiceImpl categoryService;
    private final GetCategoriesUseCase getCategoriesUseCase;
    private final GetCategoryUseCase getCategoryUseCase;
    private final CreateCategoryUseCase createCategoryUseCase;
    private final UpdateCategoryNameUseCase updateCategoryNameUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;

    //    @CrossOrigin(allowCredentials = "true")
    @GetMapping
    public ResponseEntity<GetCategoriesResponse> getAllCategories() {
        return
                ResponseEntity.ok(getCategoriesUseCase
                        .getCategories()
                );
    }

//    @CrossOrigin(allowCredentials = "true")
//    @GetMapping("/id")
//    public ResponseEntity<GetCategoryResponse> getCategoryById(@RequestParam @Valid final int categoryId) {
//        if(getCategoryUseCase.getCategoryById(categoryId) == null) {
//            return ResponseEntity.notFound().build();
//        }
//        final Category category = getCategoryUseCase.getCategoryById(categoryId).getCategory();
//        return ResponseEntity.ok(GetCategoryResponse.builder().category(category).build());
//    }

    //    @CrossOrigin(allowCredentials = "true")
    @GetMapping("/name/{categoryName}")
    public ResponseEntity<GetCategoryResponse> getCategoryByName(@PathVariable @Valid String categoryName) {
        return ResponseEntity.ok(getCategoryUseCase.getCategoryByName(categoryName));
    }

    @CrossOrigin(allowCredentials = "true")
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity createCategory(@RequestBody @Valid String categoryName) {
        CreateCategoryRequest request = CreateCategoryRequest.builder().categoryName(categoryName).build();
        createCategoryUseCase.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @CrossOrigin(allowCredentials = "true")
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @PutMapping("{categoryId}")
    public ResponseEntity updateCategory(@PathVariable @Valid int categoryId, @RequestBody @Valid String categoryName) {
        UpdateCategoryNameRequest request = UpdateCategoryNameRequest.builder().categoryId(categoryId).newCategoryName(categoryName).build();
        updateCategoryNameUseCase.updateCategoryName(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @CrossOrigin(allowCredentials = "true")
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @DeleteMapping("{categoryId}")
    public ResponseEntity deleteCategory(@PathVariable @Valid int categoryId) {
        deleteCategoryUseCase.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
