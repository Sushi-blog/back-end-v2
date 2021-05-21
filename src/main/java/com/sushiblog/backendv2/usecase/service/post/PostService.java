package com.sushiblog.backendv2.usecase.service.post;

import com.sushiblog.backendv2.usecase.dto.request.PostRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PostService {
    void writePost(PostRequest request, MultipartFile file) throws IOException;
    void updatePost(long id, PostRequest request, MultipartFile file) throws IOException;
}