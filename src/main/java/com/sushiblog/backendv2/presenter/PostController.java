package com.sushiblog.backendv2.presenter;

import com.sushiblog.backendv2.usecase.dto.request.PostRequest;
import com.sushiblog.backendv2.usecase.dto.response.PostsResponse;
import com.sushiblog.backendv2.usecase.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/sushi/blog")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void writePost(@RequestBody @Validated PostRequest request, // /blog?file=
                          @RequestParam(required = false) MultipartFile file) throws IOException {
        postService.writePost(request, file);
    }

    @PutMapping("/details")
    public void updatePost(@RequestParam long id,
                           @RequestBody PostRequest request,
                           @RequestParam(required = false) MultipartFile file) throws IOException {
        postService.updatePost(id, request, file);

    }

    @GetMapping("/{email}")
    public PostsResponse getPosts(@PathVariable String email,
                                  @RequestParam(name = "category-id", required = false) Long categoryId) {
        return postService.getPosts(email, categoryId);
    }

}
