package com.sushiblog.backendv2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sushiblog.backendv2.entity.category.Category;
import com.sushiblog.backendv2.entity.category.CategoryRepository;
import com.sushiblog.backendv2.entity.post.Post;
import com.sushiblog.backendv2.entity.post.PostRepository;
import com.sushiblog.backendv2.entity.user.User;
import com.sushiblog.backendv2.entity.user.UserRepository;
import com.sushiblog.backendv2.error.UserNotFoundException;
import com.sushiblog.backendv2.usecase.dto.request.PostRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BackEndV2Application.class)
@ActiveProfiles("test")
public class PostControllerTest {

    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository blogRepository;

    @Autowired
    private BasicTestSupport basicTestSupport;

    User user;
    Category category;
    Post post;

    @BeforeEach
    public void setup() {
        mvc = basicTestSupport.setup();

        user = basicTestSupport.createUser("201413lsy@dsm.hs.kr","대마고 이승윤");
        basicTestSupport.createUser("yyuunn17@naver.com","취업할 수 있을까");
        category = basicTestSupport.createCategory(user, "category1");
        post = basicTestSupport.createPost(user, category, "title1");
        basicTestSupport.createPost(user, category, "title2");
        basicTestSupport.createPost(user, category, "title3");
        basicTestSupport.createPost(user, category, "title4");
    }

    @AfterEach
    public void deleteAll() {
        userRepository.deleteAll();
        blogRepository.deleteAll();
        categoryRepository.deleteAll(); //매핑 관계에서는 관련 있는거 다 지우기
    }

    @WithMockUser(value = "201413lsy@dsm.hs.kr", password = "password1234")
    @Test
    public void 게시글작성_성공() throws Exception {
        PostRequest request = PostRequest.builder()
                .title("title")
                .content("content")
                .categoryId(category.getId())
                .build();

        mvc.perform(post("/sushi/blog")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    public void 게시글작성_실패_로그인X() throws Exception {
        PostRequest request = PostRequest.builder()
                .title("title")
                .content("content")
                .categoryId(category.getId())
                .build();

        mvc.perform(post("/sushi/blog")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "yyuunn17@naver.com", password = "password1234")
    @Test
    public void 게시글작성_실패_다른유저() throws Exception {
        PostRequest request = PostRequest.builder()
                .title("title")
                .content("content")
                .categoryId(category.getId())
                .build();

        mvc.perform(post("/sushi/blog")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "201413lsy@dsm.hs.kr", password = "password1234")
    @Test
    public void 게시글수정_성공() throws Exception {
        PostRequest request = PostRequest.builder()
                .title("title")
                .content("content")
                .categoryId(category.getId())
                .build();

        mvc.perform(put("/sushi/blog/details")
                .param("id",post.getId().toString())
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(value = "201413lsy@dsm.hs.kr", password = "password1234")
    @Test
    public void 게시글삭제_성공() throws Exception {
        PostRequest request = PostRequest.builder()
                .title("title")
                .content("content")
                .categoryId(category.getId())
                .build();

        mvc.perform(delete("/sushi/blog")
                .param("id",post.getId().toString())
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void 게시글목록보기() throws Exception {
        User user = userRepository.findById("201413lsy@dsm.hs.kr").orElseThrow(UserNotFoundException::new);

        mvc.perform(get("/sushi/blog/"+user.getEmail())
                .param("category-id",category.getId().toString()))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void 게시글상세보기() throws Exception {
        User user = userRepository.findById("201413lsy@dsm.hs.kr").orElseThrow(UserNotFoundException::new);

        mvc.perform(get("/sushi/blog/details")
                .param("id",post.getId().toString())).andDo(print())
                .andExpect(status().isOk()).andDo(print());
    }

}
