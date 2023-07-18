package fontys.sem3.its.meem.business.service;

import fontys.sem3.its.meem.business.exception.DuplicatedCategoryNameException;
import fontys.sem3.its.meem.business.exception.EmptyRepositoryException;
import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.its.meem.business.usecase.AccessToken.AccessTokenAuthenticityUseCase;
import fontys.sem3.its.meem.business.usecase.Category.CategoryValidatorUseCase;
import fontys.sem3.its.meem.domain.model.AccessToken;
import fontys.sem3.its.meem.domain.model.Category;
import fontys.sem3.its.meem.domain.model.UserRoleEnum;
import fontys.sem3.its.meem.domain.request.Category.CreateCategoryRequest;
import fontys.sem3.its.meem.domain.request.Category.UpdateCategoryNameRequest;
import fontys.sem3.its.meem.domain.response.Category.GetCategoriesResponse;
import fontys.sem3.its.meem.domain.response.Category.GetCategoryResponse;
import fontys.sem3.its.meem.persistence.entity.CategoryEntity;
import fontys.sem3.its.meem.persistence.entity.UserEntity;
import fontys.sem3.its.meem.persistence.repository.CategoryRepository;
import fontys.sem3.its.meem.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryValidatorUseCase categoryValidator;
    @Mock
    private AccessTokenAuthenticityUseCase accessTokenAuthenticity;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AccessToken requestAccessToken;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void getCategories_shouldReturnAllCategoriesConvertedFromEntityToRegular_whenNotEmpty() {
        CategoryEntity antiJoke = CategoryEntity.builder()
                .categoryId(1)
                .categoryName("Anti-joke")
                .build();

        CategoryEntity balkans = CategoryEntity.builder()
                .categoryId(2)
                .categoryName("Balkans")
                .build();


        when(categoryRepository.findAll()).thenReturn(List.of(
                antiJoke,
                balkans
        ));

        GetCategoriesResponse actualResponse = categoryService.getCategories();

        GetCategoriesResponse expectedResponse = GetCategoriesResponse.builder().categories(List.of(
                        Category.builder().categoryId(1).categoryName("Anti-joke").build(),
                        Category.builder().categoryId(2).categoryName("Balkans").build()))
                .build();

        assertEquals(expectedResponse, actualResponse);

        verify(categoryRepository, times(2)).findAll();
    }

    @Test
    void getCategories_shouldThrowEmptyRepositoryException_whenEmpty() {

        List<CategoryEntity> emptyList = new ArrayList<>();

        when(categoryRepository.findAll()).thenReturn(emptyList);

        EmptyRepositoryException exception = assertThrows(EmptyRepositoryException.class, () -> categoryService.getCategories());

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

        assertEquals("NO_RECORDS_FOUND", exception.getReason());

        verify(categoryRepository).findAll();

    }

    //
    @Test
    void createCategory_ShouldSaveTheCategory_WhenNameIsNonDuplicateNonNullAndAdminAuthority() {

        CategoryEntity testCategory = CategoryEntity.builder().categoryName("Test").build();

        CategoryEntity expectedSavedCategory = CategoryEntity.builder().categoryId(1).categoryName("Test").build();

        CreateCategoryRequest request = CreateCategoryRequest.builder().categoryName("Test").build();

        UserEntity user = UserEntity.builder()
                .userId(10)
                .userRole(UserRoleEnum.ADMIN)
                .build();

        when(requestAccessToken.getAuthority()).thenReturn("ADMIN");

        when(requestAccessToken.getUserId()).thenReturn(10);

        when(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false)).thenReturn(user);

        when(accessTokenAuthenticity.checkIfAuthoritiesMatch(
                userRepository.findByUserIdAndDeleted(
                        requestAccessToken.getUserId(), false),
                requestAccessToken)
        ).thenReturn(true);

        when(categoryRepository.existsByCategoryName(testCategory.getCategoryName())).thenReturn(false);

        when(categoryRepository.save(testCategory)).thenReturn(expectedSavedCategory);

        CategoryEntity actuallySavedCategory = categoryService.createCategory(request);

        assertEquals(expectedSavedCategory, actuallySavedCategory);

        verify(requestAccessToken).getAuthority();

        verify(categoryRepository).save(testCategory);

        verify(categoryRepository).existsByCategoryName(testCategory.getCategoryName());

        verify(requestAccessToken, times(3)).getUserId();

        verify(userRepository, times(2)).findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        verify(accessTokenAuthenticity)
                .checkIfAuthoritiesMatch(
                        userRepository.findByUserIdAndDeleted(
                                requestAccessToken.getUserId(), false), requestAccessToken);
    }

    @Test
    void createCategory_ShouldThrowDuplicatedCategoryNameException_WhenNameIsDuplicateAndAdminAuthority() {

        CreateCategoryRequest request = CreateCategoryRequest.builder().categoryName("Test").build();

        UserEntity user = UserEntity.builder()
                .userId(10)
                .userRole(UserRoleEnum.ADMIN)
                .build();

        when(requestAccessToken.getAuthority()).thenReturn("ADMIN");

        when(requestAccessToken.getUserId()).thenReturn(10);

        when(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false)).thenReturn(user);

        when(accessTokenAuthenticity.checkIfAuthoritiesMatch(
                userRepository.findByUserIdAndDeleted(
                        requestAccessToken.getUserId(), false),
                requestAccessToken)
        ).thenReturn(true);

        when(categoryRepository.existsByCategoryName(request.getCategoryName())).thenReturn(true);

        DuplicatedCategoryNameException exception = assertThrows(DuplicatedCategoryNameException.class, () -> categoryService.createCategory(request));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        assertEquals("DUPLICATED_CATEGORY_NAME", exception.getReason());

        verify(requestAccessToken).getAuthority();

        verify(requestAccessToken, times(3)).getUserId();

        verify(userRepository, times(2)).findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        verify(accessTokenAuthenticity)
                .checkIfAuthoritiesMatch(
                        userRepository.findByUserIdAndDeleted(
                                requestAccessToken.getUserId(), false), requestAccessToken);

        verify(categoryRepository).existsByCategoryName(request.getCategoryName());
    }

    @Test
    void createCategory_ShouldThrowUnauthorizedDataAccessException_WhenNotAdminAuthority() {

        CreateCategoryRequest request = CreateCategoryRequest.builder().categoryName("Test").build();

        UserEntity user = UserEntity.builder()
                .userId(10)
                .userRole(UserRoleEnum.REGULAR)
                .build();

        when(requestAccessToken.getAuthority()).thenReturn("REGULAR");

        when(requestAccessToken.getUserId()).thenReturn(10);

        when(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false)).thenReturn(user);

        when(accessTokenAuthenticity.checkIfAuthoritiesMatch(
                userRepository.findByUserIdAndDeleted(
                        requestAccessToken.getUserId(), false),
                requestAccessToken)
        ).thenReturn(true);

        UnauthorizedDataAccessException exception = assertThrows(UnauthorizedDataAccessException.class, () -> categoryService.createCategory(request));

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());

        assertEquals("NOT_PERMITTED", exception.getReason());

        verify(requestAccessToken).getAuthority();

        verify(requestAccessToken, times(3)).getUserId();

        verify(userRepository, times(2)).findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        verify(accessTokenAuthenticity)
                .checkIfAuthoritiesMatch(
                        userRepository.findByUserIdAndDeleted(
                                requestAccessToken.getUserId(), false), requestAccessToken);

    }

    @Test
    void deleteCategory_ShouldCallDeleteQueryMethod_WhenChecksPass() {

        int categoryId = 69;

        UserEntity user = UserEntity.builder()
                .userId(10)
                .userRole(UserRoleEnum.ADMIN)
                .build();

        when(requestAccessToken.getAuthority()).thenReturn("ADMIN");

        when(requestAccessToken.getUserId()).thenReturn(10);

        when(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false)).thenReturn(user);

        when(accessTokenAuthenticity.checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken)).thenReturn(true);

        when(categoryValidator.validateId(categoryId)).thenReturn(true);

        when(categoryRepository.deleteByCategoryId(categoryId)).thenReturn(1);

        categoryService.deleteCategory(categoryId);

        verify(requestAccessToken).getAuthority();

        verify(categoryValidator).validateId(categoryId);

        verify(categoryRepository).deleteByCategoryId(categoryId);

        verify(requestAccessToken, times(3)).getUserId();

        verify(userRepository, times(2)).findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        verify(accessTokenAuthenticity).checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken);

    }

    @Test
    void deleteCategory_ShouldThrowInvalidIdentificationException_WhenIdCheckFails() {

        //arrange
        int categoryId = 69;

        UserEntity user = UserEntity.builder()
                .userId(10)
                .userRole(UserRoleEnum.ADMIN)
                .build();

        when(requestAccessToken.getAuthority()).thenReturn("ADMIN");

        when(requestAccessToken.getUserId()).thenReturn(10);

        when(requestAccessToken.getAuthority()).thenReturn("ADMIN");

        when(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false)).thenReturn(user);

        when(accessTokenAuthenticity.checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken)).thenReturn(true);

        when(categoryValidator.validateId(categoryId)).thenThrow(new InvalidIdentificationException());

        //act
        InvalidIdentificationException exception = assertThrows(InvalidIdentificationException.class, () -> categoryService.deleteCategory(categoryId));

        //assert
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

        assertEquals("INVALID_IDENTIFIER", exception.getReason());

        verify(requestAccessToken).getAuthority();

        verify(categoryValidator).validateId(categoryId);

        verify(requestAccessToken, times(3)).getUserId();

        verify(userRepository, times(2)).findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        verify(accessTokenAuthenticity).checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken);

    }

    @Test
    void deleteCategory_ShouldThrowUnauthorizedDataAccessException_WhenNotAdminAuthority() {

        int categoryId = 69;

        UserEntity user = UserEntity.builder()
                .userId(10)
                .userRole(UserRoleEnum.REGULAR)
                .build();

        when(requestAccessToken.getAuthority()).thenReturn("REGULAR");

        when(requestAccessToken.getUserId()).thenReturn(10);

        when(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false)).thenReturn(user);

        when(accessTokenAuthenticity.checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken)).thenReturn(true);

        UnauthorizedDataAccessException exception = assertThrows(UnauthorizedDataAccessException.class, () -> categoryService.deleteCategory(categoryId));

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());

        assertEquals("NOT_PERMITTED", exception.getReason());

        verify(requestAccessToken).getAuthority();

        verify(requestAccessToken, times(3)).getUserId();

        verify(userRepository, times(2)).findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        verify(accessTokenAuthenticity).checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken);
    }

    @Test
    void getCategoryByName_ShouldReturnGetCategoryResponse_whenChecksPass() {

        String categoryName = "Dank";

        CategoryEntity returnedEntity = CategoryEntity.builder().categoryId(5).categoryName("Dank").build();

        Category convertedCategory = Category.builder().categoryId(5).categoryName("Dank").build();

        GetCategoryResponse expectedResponse = GetCategoryResponse.builder().category(convertedCategory).build();

        when(categoryRepository.existsByCategoryName(categoryName)).thenReturn(true);

        when(categoryRepository.findByCategoryName(categoryName)).thenReturn(returnedEntity);

        GetCategoryResponse actualResponse = categoryService.getCategoryByName(categoryName);

        assertEquals(expectedResponse, actualResponse);

        verify(categoryRepository).existsByCategoryName(categoryName);

        verify(categoryRepository).findByCategoryName(categoryName);

    }

    @Test
    void getCategoryByName_ShouldThrowInvalidIdentificationException_WhenIdCheckFails() {

        String categoryName = "Danke";

        when(categoryRepository.existsByCategoryName(categoryName)).thenReturn(false);

        InvalidIdentificationException exception = assertThrows(InvalidIdentificationException.class, () -> categoryService.getCategoryByName(categoryName));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

        assertEquals("INVALID_IDENTIFIER", exception.getReason());

        verify(categoryRepository).existsByCategoryName(categoryName);

    }

    @Test
    void updateCategoryName_ShouldThrowDuplicatedCategoryNameException_WhenNameIsAlreadyInDB() {

        UpdateCategoryNameRequest request = UpdateCategoryNameRequest.builder().categoryId(5).newCategoryName("Danke").build();

        UserEntity user = UserEntity.builder()
                .userId(10)
                .userRole(UserRoleEnum.REGULAR)
                .build();

        when(requestAccessToken.getUserId()).thenReturn(10);

        when(requestAccessToken.getAuthority()).thenReturn("ADMIN");

        when(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false)).thenReturn(user);

        when(accessTokenAuthenticity.checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken)).thenReturn(true);

        when(categoryRepository.existsByCategoryName(request.getNewCategoryName())).thenReturn(true);

        DuplicatedCategoryNameException exception = assertThrows(DuplicatedCategoryNameException.class, () -> categoryService.updateCategoryName(request));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        assertEquals("DUPLICATED_CATEGORY_NAME", exception.getReason());

        verify(categoryRepository).existsByCategoryName(request.getNewCategoryName());

        verify(requestAccessToken).getAuthority();

        verify(categoryValidator).validateId(request.getCategoryId());

        verify(requestAccessToken, times(3)).getUserId();

        verify(userRepository, times(2)).findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        verify(accessTokenAuthenticity).checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken);
    }

    @Test
    void updateCategoryName_ShouldUpdateName_WhenChecksPass() {

        UpdateCategoryNameRequest request = UpdateCategoryNameRequest.builder().categoryId(5).newCategoryName("Danke").build();

        UserEntity user = UserEntity.builder()
                .userId(10)
                .userRole(UserRoleEnum.ADMIN)
                .build();

        when(requestAccessToken.getAuthority()).thenReturn("ADMIN");

        when(requestAccessToken.getUserId()).thenReturn(10);

        when(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false)).thenReturn(user);

        when(accessTokenAuthenticity.checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken)).thenReturn(true);

        when(categoryValidator.validateId(request.getCategoryId())).thenReturn(true);

        when(categoryRepository.existsByCategoryName(request.getNewCategoryName())).thenReturn(false);

        when(categoryRepository.updateCategoryName(request.getCategoryId(), request.getNewCategoryName())).thenReturn(1);

        categoryService.updateCategoryName(request);

        verify(categoryValidator).validateId(request.getCategoryId());

        verify(categoryRepository).existsByCategoryName(request.getNewCategoryName());

        verify(categoryRepository).updateCategoryName(request.getCategoryId(), request.getNewCategoryName());

        verify(requestAccessToken).getAuthority();

        verify(requestAccessToken, times(3)).getUserId();

        verify(userRepository, times(2)).findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        verify(accessTokenAuthenticity).checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken);
    }

    @Test
    void updateCategoryName_ShouldThrowUnauthorizedDataAccessException_WhenNotAdminAuthority() {

        UpdateCategoryNameRequest request = UpdateCategoryNameRequest.builder().categoryId(5).newCategoryName("Danke").build();

        UserEntity user = UserEntity.builder()
                .userId(10)
                .userRole(UserRoleEnum.REGULAR)
                .build();


        when(requestAccessToken.getAuthority()).thenReturn("REGULAR");

        when(requestAccessToken.getUserId()).thenReturn(10);

        when(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false)).thenReturn(user);

        when(accessTokenAuthenticity.checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken)).thenReturn(true);

        UnauthorizedDataAccessException exception = assertThrows(UnauthorizedDataAccessException.class, () -> categoryService.updateCategoryName(request));

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());

        assertEquals("NOT_PERMITTED", exception.getReason());

        verify(requestAccessToken).getAuthority();

        verify(requestAccessToken, times(3)).getUserId();

        verify(userRepository, times(2)).findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        verify(accessTokenAuthenticity).checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken);
    }

}