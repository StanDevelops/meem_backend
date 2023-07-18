package fontys.sem3.its.meem.business.service;

import fontys.sem3.its.meem.business.exception.DuplicatedCategoryNameException;
import fontys.sem3.its.meem.business.exception.EmptyRepositoryException;
import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.its.meem.business.service.converter.CategoryConverter;
import fontys.sem3.its.meem.business.usecase.AccessToken.AccessTokenAuthenticityUseCase;
import fontys.sem3.its.meem.business.usecase.Category.*;
import fontys.sem3.its.meem.domain.model.AccessToken;
import fontys.sem3.its.meem.domain.model.Category;
import fontys.sem3.its.meem.domain.request.Category.CreateCategoryRequest;
import fontys.sem3.its.meem.domain.request.Category.UpdateCategoryNameRequest;
import fontys.sem3.its.meem.domain.response.Category.GetCategoriesResponse;
import fontys.sem3.its.meem.domain.response.Category.GetCategoryResponse;
import fontys.sem3.its.meem.persistence.entity.CategoryEntity;
import fontys.sem3.its.meem.persistence.entity.UserEntity;
import fontys.sem3.its.meem.persistence.repository.CategoryRepository;
import fontys.sem3.its.meem.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements
        GetCategoriesUseCase,
        GetCategoryUseCase,
        CreateCategoryUseCase,
        UpdateCategoryNameUseCase,
        DeleteCategoryUseCase {
    private final CategoryRepository categoryRepository;
    private final CategoryValidatorUseCase categoryValidator;
    private final UserRepository userRepository;
    private final AccessTokenAuthenticityUseCase accessTokenAuthenticity;
    private final AccessToken requestAccessToken;

    @Override
    public GetCategoriesResponse getCategories() {

        if (categoryRepository.findAll().isEmpty()) {
            throw new EmptyRepositoryException();
        }

        List<Category> categories = categoryRepository //find and get all existing entities and convert them into list
                .findAll()
                .stream()
                .map(CategoryConverter::convert)
                .toList();


        return GetCategoriesResponse.builder()
                .categories(categories)
                .build();
    }

    @Transactional
    @Override
    public CategoryEntity createCategory(@NotNull @NotBlank CreateCategoryRequest request) {

        UserEntity user = userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        accessTokenAuthenticity.checkIfAuthoritiesMatch(user, requestAccessToken);

        if (!Objects.equals(requestAccessToken.getAuthority(), "ADMIN")) {
            throw new UnauthorizedDataAccessException("NOT_PERMITTED");
        }

        if (categoryRepository.existsByCategoryName(request.getCategoryName())) //check if a DB entity already matches the request's parameter
        {
            throw new DuplicatedCategoryNameException(); //A MATCH! NOT GOOD! Throw a hissy!
        }
        /* declare and construct temporary entity with supplied name */
        CategoryEntity tempCategory = new CategoryEntity().builder()
                .categoryName(request.getCategoryName())
                .build();
        /* attempt to save the temporary entity and override it with the one returned by the repository after saving */
        return categoryRepository.save(tempCategory);

    }

    @Transactional
    @Override
    public void deleteCategory(@NotNull int categoryId) {

        UserEntity user = userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        accessTokenAuthenticity.checkIfAuthoritiesMatch(user, requestAccessToken);

        if (!Objects.equals(requestAccessToken.getAuthority(), "ADMIN")) {
            throw new UnauthorizedDataAccessException("NOT_PERMITTED");
        }

        // validate if given ID belongs to an entity from DB
        //then and 'InvalidIdentificationException' is thrown
        categoryValidator.validateId(categoryId);

          /*
            if the checks above were passed and no exception was thrown, proceed to deletion
         */
        categoryRepository.deleteByCategoryId(categoryId);
    }

    @Override
    public GetCategoryResponse getCategoryByName(@NotBlank String categoryName) {

        if (!categoryRepository.existsByCategoryName(categoryName)) //check if a DB entity already matches the request's parameter
        {
            throw new InvalidIdentificationException(); //NO MATCH! NOT GOOD! Throw a hissy!
        }

        CategoryEntity returnedCategory = categoryRepository.findByCategoryName(categoryName);
        /*
            if the checks above were passed and no exception was thrown during retrieval of the entity
            then return a response entity with ok status whose body is the converted entity
         */
        return GetCategoryResponse.builder().category(CategoryConverter.convert(returnedCategory)).build();

    }

    @Override
    public void updateCategoryName(@NotNull @NotBlank UpdateCategoryNameRequest request) {

        accessTokenAuthenticity.checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken);

        if (!Objects.equals(requestAccessToken.getAuthority(), "ADMIN")) {
            throw new UnauthorizedDataAccessException("NOT_PERMITTED");
        }

        // validate if given ID belongs to an entity from DB
        //throws an 'InvalidIdentificationException'
        categoryValidator.validateId(request.getCategoryId());

        if (categoryRepository.existsByCategoryName(request.getNewCategoryName())) //check if a DB entity already matches the request's parameter
        {

            throw new DuplicatedCategoryNameException(); //A MATCH! NOT GOOD! Throw a hissy!

        } else {

            categoryRepository.updateCategoryName(request.getCategoryId(), request.getNewCategoryName());

        }
    }

}