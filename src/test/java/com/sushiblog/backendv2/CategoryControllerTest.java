package com.sushiblog.backendv2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sushiblog.backendv2.entity.category.Category;
import com.sushiblog.backendv2.entity.category.CategoryRepository;
import com.sushiblog.backendv2.entity.post.PostRepository;
import com.sushiblog.backendv2.entity.user.User;
import com.sushiblog.backendv2.entity.user.UserRepository;
import com.sushiblog.backendv2.error.UserNotFoundException;
import com.sushiblog.backendv2.usecase.dto.request.UpdateCategoryNameRequest;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BackEndV2Application.class)
@ActiveProfiles("test")
class CategoryControllerTest {

    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository blogRepository;

    @Autowired
    private BasicTestSupport basicTestSupport;

    @BeforeEach
    public void setup() {
        mvc = basicTestSupport.setup();

        basicTestSupport.createUser("201413lsy@dsm.hs.kr","대마고 이승윤");
        basicTestSupport.createUser("yyuunn17@naver.com","취업할 수 있을까");
    }

    @AfterEach
    public void deleteAll() {
        userRepository.deleteAll();
        blogRepository.deleteAll();
        categoryRepository.deleteAll(); //매핑 관계에서는 관련 있는거 다 지우기
    }

    @WithMockUser(value = "201413lsy@dsm.hs.kr", password = "password1234")
    @Test
    public void 카테고리명수정() throws Exception {
        Long id = createCategory(userRepository.findById("201413lsy@dsm.hs.kr").orElseThrow(UserNotFoundException::new));

        UpdateCategoryNameRequest request = new UpdateCategoryNameRequest(id,"sushi카테고리");

        mvc.perform(put("/category")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(value = "201413lsy@dsm.hs.kr", password = "password1234")
    @Test
    public void 카테고리명수정_실패() throws Exception {
        Long id = createCategory(userRepository.findById("201413lsy@dsm.hs.kr").orElseThrow(UserNotFoundException::new));
        UpdateCategoryNameRequest request = new UpdateCategoryNameRequest(id,"sushi카테고리20자 넘지 않았냐 왜 오류 안뜨냐");

        mvc.perform(put("/category")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(value = "yyuunn17@naver.com", password = "password1235")
    @Test
    public void 카테고리명수정_주인X() throws Exception {
        Long id = createCategory(userRepository.findById("201413lsy@dsm.hs.kr").orElseThrow(UserNotFoundException::new));
        UpdateCategoryNameRequest request = new UpdateCategoryNameRequest(id,"sushi");

        mvc.perform(put("/category")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized());
    }

    private Long createCategory(User user) {
        return categoryRepository.save(
                Category.builder()
                        .user(user)
                        .name("category1")
                        .build()
        ).getId();
    }

    @Test
    public void 카테고리리스트() throws Exception {
        mvc.perform(get("/category/201413lsy@dsm.hs.kr"))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void 카테고리리스트_가져오기실패() throws Exception {
        mvc.perform(get("/category/201413lsy0@dsm.hs.kr"))
                .andExpect(status().isNotFound());
    }

}
