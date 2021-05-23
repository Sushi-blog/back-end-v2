package com.sushiblog.backendv2.presenter;

import com.sushiblog.backendv2.usecase.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RequestMapping("/blog/file")
@RequiredArgsConstructor
@RestController
public class FileController {

    private final PostService postService;

    @GetMapping
    public byte[] getImages(String path) throws IOException {
        return postService.getImages(path);
    }

    @DeleteMapping
    public void deleteFile(Long id) {
        postService.deleteFile(id);
    }

}
