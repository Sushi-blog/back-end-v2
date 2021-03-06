package com.sushiblog.backendv2.usecase.service.post;

import com.sushiblog.backendv2.entity.category.Category;
import com.sushiblog.backendv2.entity.category.CategoryRepository;
import com.sushiblog.backendv2.entity.post.Post;
import com.sushiblog.backendv2.entity.post.PostRepository;
import com.sushiblog.backendv2.entity.user.User;
import com.sushiblog.backendv2.entity.user.UserRepository;
import com.sushiblog.backendv2.error.*;
import com.sushiblog.backendv2.security.auth.AuthenticationFacade;
import com.sushiblog.backendv2.usecase.dto.request.PostRequest;
import com.sushiblog.backendv2.usecase.dto.response.PostDetailResponse;
import com.sushiblog.backendv2.usecase.dto.response.PostResponse;
import com.sushiblog.backendv2.usecase.dto.response.PostsResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

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
                        .imagePath(filePath)
                        .build()
        );
        if (file != null) {
            file.transferTo(new File(filePath));
        }
    }

    @Override
    public void updatePost(Long id, PostRequest request, MultipartFile file) throws IOException {
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
        if(post.getImagePath()!=null) {
            deleteImage(post.getId());
        }
        post.update(request, filePath);

        if (file != null) {
            file.transferTo(new File(filePath));
        }
        postRepository.save(post);
    }

    @Override
    public PostsResponse getPosts(String email, Long categoryId) {
        User user = userRepository.findById(email)
                .orElseThrow(UserNotFoundException::new);

        if (categoryId == 0) {
            List<PostResponse> postResponses = new ArrayList<>();
            for(Post post : user.getPosts()) {
                postResponses.add(
                        PostResponse.builder()
                                .id(post.getId())
                                .category(post.getCategory().getName())
                                .title(post.getTitle())
                                .createdAt(post.getCreatedAt())
                                .build()
                );
            }
            return new PostsResponse(postResponses);
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);
        List<Post> posts = new ArrayList<>();
        List<PostResponse> postResponses = new ArrayList<>();

        if(category.getUser().equals(user)) {
            posts = category.getPosts();
        }

        for(Post post : posts) {
            postResponses.add(
                    PostResponse.builder()
                            .id(post.getId())
                            .category(post.getCategory().getName())
                            .title(post.getTitle())
                            .createdAt(post.getCreatedAt())
                            .build()
            );
        }

        return new PostsResponse(postResponses);
    }

    @Override
    public PostDetailResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        return PostDetailResponse.builder()
                .writer(post.getUser().getNickname())
                .title(post.getTitle())
                .createdAt(post.getCreatedAt())
                .category(post.getCategory().getName())
                .content(post.getContent())
                .filePath(post.getImagePath())
                .build();
    }

    @Override
    public void deletePost(Long id) {
        User user = authenticationFacade.checkAuth();
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        if(!post.getUser().equals(user)) {
            throw new NotAccessibleException();
        }

        if(post.getImagePath() != null) {
            deleteImage(id);
        }
        postRepository.delete(post);
    }

    @Override
    public byte[] getImages(String path) throws IOException {
        File file = new File(path);
        if(!file.exists()) throw new ImageNotFoundException();

        InputStream inputStream = new FileInputStream(file);

        return IOUtils.toByteArray(inputStream);
    }

    @Override
    public void deleteFile(Long id) {
        deleteImage(id);
    }

    private void deleteImage(Long postId) {
        User user = authenticationFacade.checkAuth();
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if(post.getUser().equals(user)) {
            File file = new File(post.getImagePath());
            file.delete();
        } else {
            throw new NotAccessibleException();
        }
    }

}
