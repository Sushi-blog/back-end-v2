package com.sushiblog.backendv2.usecase.service.post;

import com.sushiblog.backendv2.usecase.dto.request.PostRequest;
import com.sushiblog.backendv2.usecase.dto.response.PostDetailResponse;
import com.sushiblog.backendv2.usecase.dto.response.PostsResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PostService {
    void writePost(PostRequest request, MultipartFile file) throws IOException;
    void updatePost(Long id, PostRequest request, MultipartFile file) throws IOException;
    PostsResponse getPosts(String email, Long categoryId);
    PostDetailResponse getPost(Long id);
    void deletePost(Long id);

    byte[] getImages(String path) throws IOException;
    void deleteFile(Long id);
}
