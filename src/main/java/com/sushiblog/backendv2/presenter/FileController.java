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

    @GetMapping("/{path}")
    public byte[] getImages(@PathVariable String path) throws IOException {
        return postService.getImages(path);
    }

    @DeleteMapping("/{id}")
    public void deleteFile(@PathVariable Long id) {
        postService.deleteFile(id);
    }

}
