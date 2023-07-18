package fontys.sem3.its.meem.controller;

import fontys.sem3.its.meem.business.usecase.PostRating.*;
import fontys.sem3.its.meem.config.security.isauthenticated.IsAuthenticated;
import fontys.sem3.its.meem.domain.model.FrontendPostRatings;
import fontys.sem3.its.meem.domain.request.PostRating.ChangePostRatingRequest;
import fontys.sem3.its.meem.domain.request.PostRating.CreatePostRatingRequest;
import fontys.sem3.its.meem.domain.response.PostRating.AverageGroupRatingResponse;
import fontys.sem3.its.meem.domain.response.PostRating.CreatePostRatingResponse;
import fontys.sem3.its.meem.domain.response.PostRating.GetPostRatingResponse;
import fontys.sem3.its.meem.domain.response.PostRating.GetPostRatingsResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/postratings")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000/"})
public class PostRatingController {
    private final GetPostRatingUseCase getPostRatingUseCase;
    private final GetPostRatingsUseCase getPostRatingsUseCase;
    private final CreatePostRatingUseCase createPostRatingUseCase;
    private final ChangePostRatingUseCase changePostRatingUseCase;
    private final DeletePostRatingUseCase deletePostRatingUseCase;
    private final GetAveragePostRatingPerGroupUseCase getAveragePostRatingPerGroupUseCase;

    @CrossOrigin(allowCredentials = "true")
    @IsAuthenticated
    @RolesAllowed({"ROLE_REGULAR", "ROLE_MOD", "ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<CreatePostRatingResponse> createPostRating(@RequestBody @Valid CreatePostRatingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createPostRatingUseCase.createPostRating(request));
    }

    @GetMapping("/combined/post/{postUrl}")
    public ResponseEntity<FrontendPostRatings> getCombinedPostRatingsForPostByUrl(@PathVariable @Valid final @NotBlank String postUrl) {
        return ResponseEntity.status(HttpStatus.OK).body(getPostRatingsUseCase.getCombinedRatingsCountForAPostByUrl(URLDecoder.decode(postUrl, StandardCharsets.UTF_8)));
    }


    @GetMapping("/combined/group")
    public ResponseEntity<List<FrontendPostRatings>> getCombinedPostRatingsForAllPostsInSortingGroup(@RequestParam @Valid final int groupId) {
        return ResponseEntity.status(HttpStatus.OK).body(getPostRatingsUseCase.getCombinedRatingsCountForAllPostsInSortingGroup(groupId));
    }

    @GetMapping("/combined/group-category")
    public ResponseEntity<List<FrontendPostRatings>> getCombinedPostRatingsForAllPostsInSortingGroupAndCategory(@RequestParam @Valid final int groupId, @RequestParam @Valid final int categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(getPostRatingsUseCase.getCombinedRatingsCountForAllPostsInSortingGroupAndCategory(groupId, categoryId));
    }


    @CrossOrigin(allowCredentials = "true")
    @IsAuthenticated
    @RolesAllowed({"ROLE_REGULAR", "ROLE_MOD", "ROLE_ADMIN", "ROLE_BANNED"})
    @GetMapping("/user/post/{postUrl}")
    public ResponseEntity<GetPostRatingResponse> getPostRatingOfPostFromUserByPostUrl(@PathVariable @Valid final @NotBlank String postUrl, @RequestParam @Valid final int userId) {
        return ResponseEntity.status(HttpStatus.OK).body(getPostRatingUseCase.getPostRatingFromUserByPostUrl(URLDecoder.decode(postUrl, StandardCharsets.UTF_8), userId));
    }

    @CrossOrigin(allowCredentials = "true")
    @IsAuthenticated
    @RolesAllowed({"ROLE_REGULAR", "ROLE_MOD", "ROLE_ADMIN", "ROLE_BANNED"})
    @GetMapping("/user/group")
    public ResponseEntity<GetPostRatingsResponse> getPostRatingsOfPostFromUserBySortingGroup(@RequestParam @Valid final int userId, @RequestParam @Valid final int groupId) {
        return ResponseEntity.status(HttpStatus.OK).body(getPostRatingsUseCase.getPostRatingsFromUserBySortingGroup(userId, groupId));
    }

    @CrossOrigin(allowCredentials = "true")
    @IsAuthenticated
    @RolesAllowed({"ROLE_REGULAR", "ROLE_MOD", "ROLE_ADMIN", "ROLE_BANNED"})
    @GetMapping("/user/group-category")
    public ResponseEntity<GetPostRatingsResponse> getPostRatingsFromUserBySortingGroupAndCategory(@RequestParam @Valid final int userId, @RequestParam @Valid final int groupId, @RequestParam @Valid final int categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(getPostRatingsUseCase.getPostRatingsFromUserBySortingGroupAndCategory(userId, groupId, categoryId));
    }

    @CrossOrigin(allowCredentials = "true")
    @IsAuthenticated
    @RolesAllowed({"ROLE_REGULAR", "ROLE_MOD", "ROLE_ADMIN"})
    @PutMapping("/updateRating")
    public ResponseEntity<FrontendPostRatings> updatePostRating(@RequestBody @Valid ChangePostRatingRequest request) {

        FrontendPostRatings returnedRatings = changePostRatingUseCase.changePostRating(request);

        return ResponseEntity.status(HttpStatus.OK).body(returnedRatings);

    }

    @CrossOrigin(allowCredentials = "true")
    @IsAuthenticated
    @RolesAllowed({"ROLE_REGULAR", "ROLE_MOD", "ROLE_ADMIN", "ROLE_BANNED"})
    @DeleteMapping("/{ratingId}")
    public ResponseEntity<FrontendPostRatings> removePostRating(@PathVariable int ratingId, @RequestParam int postId, @RequestParam int userId) {
        return ResponseEntity.status(HttpStatus.OK).body(deletePostRatingUseCase.deletePostRating(ratingId, postId, userId));
    }

    @CrossOrigin(allowCredentials = "true")
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping("/stats/group-average")
    public ResponseEntity<AverageGroupRatingResponse> getAveragePostRatingPerGroup() {
        return ResponseEntity.status(HttpStatus.OK).body(getAveragePostRatingPerGroupUseCase.getAverageGroupRatingStats());
    }

}
