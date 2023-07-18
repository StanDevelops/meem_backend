package fontys.sem3.its.meem.controller;

import fontys.sem3.its.meem.business.usecase.Post.*;
import fontys.sem3.its.meem.config.security.isauthenticated.IsAuthenticated;
import fontys.sem3.its.meem.domain.model.FrontendPost;
import fontys.sem3.its.meem.domain.request.Post.ChangePostTitleRequest;
import fontys.sem3.its.meem.domain.request.Post.CreatePostRequest;
import fontys.sem3.its.meem.domain.response.Post.GetPostResponse;
import fontys.sem3.its.meem.domain.response.Post.GetPostsResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

//done
@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000/"})
public class PostController {
    CreatePostUseCase createPostUseCase;
    ChangePostTitleUseCase changePostTitleUseCase;
    DeletePostUseCase deletePostUseCase;
    GetPostUseCase getPostUseCase;
    GetPostsUseCase getPostsUseCase;

    //    @CrossOrigin(allowCredentials = "true")
    @GetMapping("{postId}")
    public ResponseEntity<GetPostResponse> getPostById(@PathVariable @Valid final int postId) {
        return ResponseEntity.ok(getPostUseCase.getPostById(postId));
    }

    //    @CrossOrigin(allowCredentials = "true")
    @GetMapping("/view/{postUrl}")
    public ResponseEntity<GetPostResponse> getPostByUrl(@PathVariable @Valid final String postUrl) {
        return ResponseEntity.ok(getPostUseCase.getPostByUrl(postUrl));
    }


    //    @CrossOrigin(allowCredentials = "true")
    @GetMapping("/sorting")
    public ResponseEntity<GetPostsResponse> getPostsBySortingGroupId(@RequestParam @Valid final int groupId) {
        return ResponseEntity.ok(getPostsUseCase.getPostsBySortingGroup(groupId));
    }

    //    @CrossOrigin(allowCredentials = "true")
    @GetMapping("/sorting/category")
    public ResponseEntity<GetPostsResponse> getPostsBySortingGroupAndCategoryId(@RequestParam @Valid final int groupId, @RequestParam @Valid final int categoryId) {
        return ResponseEntity.ok(getPostsUseCase.getPostsBySortingGroupAndCategory(groupId, categoryId));
    }


    @CrossOrigin(allowCredentials = "true")
    @IsAuthenticated
    @RolesAllowed({"ROLE_REGULAR", "ROLE_MOD", "ROLE_ADMIN"})
    @PostMapping("/external")
//    public ResponseEntity<Boolean> createPost(@RequestParam @Valid final String postTitle,
//                                              @RequestParam @Valid final String postBody,
//                                              @RequestParam @Valid final int authorId,
//                                              @RequestParam @Valid final int categoryId) {

    public ResponseEntity<FrontendPost> createPost(@RequestBody @Valid CreatePostRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createPostUseCase.createPostWithExternalMedia(request));
    }

    @CrossOrigin(allowCredentials = "true")
    @IsAuthenticated
    @RolesAllowed({"ROLE_REGULAR", "ROLE_MOD", "ROLE_ADMIN"})
    @PutMapping("/updateTitle")
    public ResponseEntity updatePostTitle(@RequestBody @Valid ChangePostTitleRequest request) {
        changePostTitleUseCase.changeTitle(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @CrossOrigin(allowCredentials = "true")
    @IsAuthenticated
    @RolesAllowed({"ROLE_REGULAR", "ROLE_MOD", "ROLE_ADMIN"})
    @DeleteMapping("/{postId}")
    public ResponseEntity deletePost(@PathVariable @Valid int postId) {
        deletePostUseCase.deletePost(postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
