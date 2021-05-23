package com.sushiblog.backendv2.presenter;

import com.sushiblog.backendv2.usecase.dto.request.PostRequest;
import com.sushiblog.backendv2.usecase.dto.response.PostDetailResponse;
import com.sushiblog.backendv2.usecase.dto.response.PostsResponse;
import com.sushiblog.backendv2.usecase.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RequestMapping("/blog")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void writePost(@RequestBody @Valid PostRequest request, // /blog?file=
                          @RequestParam(required = false) MultipartFile file) throws IOException {
        postService.writePost(request, file);
    }

    @PutMapping("/details")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePost(@RequestParam long id,
                           @RequestBody PostRequest request,
                           @RequestParam(required = false) MultipartFile file) throws IOException {
        postService.updatePost(id, request, file);

    }

    @GetMapping("/{email}")
    public PostsResponse getPosts(@PathVariable String email,
                                  @RequestParam(name = "category-id") Long categoryId) {
        return postService.getPosts(email, categoryId);
    }

    @GetMapping("/details")
    public PostDetailResponse getPost(@RequestParam Long id) {
        return postService.getPost(id);
    }

    @DeleteMapping
    public void deletePost(@RequestParam Long id) {
        postService.deletePost(id);
    }

}
