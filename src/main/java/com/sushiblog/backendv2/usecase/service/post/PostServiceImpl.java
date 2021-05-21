package com.sushiblog.backendv2.usecase.service.post;

import com.sushiblog.backendv2.entity.category.Category;
import com.sushiblog.backendv2.entity.category.CategoryRepository;
import com.sushiblog.backendv2.entity.post.Post;
import com.sushiblog.backendv2.entity.post.PostRepository;
import com.sushiblog.backendv2.entity.user.User;
import com.sushiblog.backendv2.error.CategoryNotFoundException;
import com.sushiblog.backendv2.error.NotAccessibleException;
import com.sushiblog.backendv2.error.PostNotFoundException;
import com.sushiblog.backendv2.security.auth.AuthenticationFacade;
import com.sushiblog.backendv2.usecase.dto.request.PostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    private final AuthenticationFacade authenticationFacade;

    @Value("${file.path}")
    private String PATH;

    @Override
    public void writePost(PostRequest request, MultipartFile file) throws IOException {
        User user = authenticationFacade.checkAuth();
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        String filePath = null;
        if(file != null) {
            filePath = PATH + file.getOriginalFilename();
        }

        if(!category.getUser().equals(user)) {
            throw new NotAccessibleException();
        }

        postRepository.save(
                Post.builder()
                        .user(user)
                        .category(category)
                        .title(request.getTitle())
                        .content(request.getContent())
                        .filePath(filePath)
                        .build()
        );
        if (file != null) {
            file.transferTo(new File(filePath));
        }
    }

    @Override
    public void updatePost(long id, PostRequest request, MultipartFile file) throws IOException {
        User user = authenticationFacade.checkAuth();
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        String filePath = null;
        if(file != null) {
            filePath = PATH + file.getOriginalFilename();
        }

        if(!category.getUser().equals(user)) {
            throw new NotAccessibleException();
        }

        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        post.update(request, filePath);

        if (file != null) {
            file.transferTo(new File(filePath));
        }
        postRepository.save(post);
    }


}
