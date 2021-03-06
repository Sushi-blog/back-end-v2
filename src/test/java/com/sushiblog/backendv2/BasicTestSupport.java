package com.sushiblog.backendv2;

import com.sushiblog.backendv2.entity.category.Category;
import com.sushiblog.backendv2.entity.category.CategoryRepository;
import com.sushiblog.backendv2.entity.post.Post;
import com.sushiblog.backendv2.entity.post.PostRepository;
import com.sushiblog.backendv2.entity.user.User;
import com.sushiblog.backendv2.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Component
public class BasicTestSupport {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public MockMvc setup() {
        return MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    public User createUser(String email, String name) {
        return userRepository.save(
                User.builder()
                        .nickname(name)
                        .email(email)
                        .password(passwordEncoder.encode("password1234"))
                        .build()
        );
    }

    public Category createCategory(User user, String categoryName) {
        return categoryRepository.save(
                Category.builder()
                        .user(user)
                        .name(categoryName)
                        .build()
        );
    }

    public Post createPost(User user, Category category, String title) {
        return postRepository.save(
                Post.builder()
                        .user(user)
                        .category(category)
                        .title(title)
                        .content("????????? ????????? ??????????????????.")
                        .build()
        );
    }

}
