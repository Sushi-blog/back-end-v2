package com.sushiblog.backendv2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sushiblog.backendv2.entity.category.CategoryRepository;
import com.sushiblog.backendv2.entity.post.PostRepository;
import com.sushiblog.backendv2.entity.user.UserRepository;
import com.sushiblog.backendv2.usecase.dto.request.SignInRequest;
import com.sushiblog.backendv2.usecase.dto.request.SignUpRequest;
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
class UserControllerTest {

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

    @Test
    public void 회원가입_중복() throws Exception {
        SignUpRequest request = new SignUpRequest("201413lsy@dsm.hs.kr","password1234","파일은 어떻게하냐");

        mvc.perform(post("/sushi/account")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isConflict());
    }

    @Test
    public void 회원가입() throws Exception {
        SignUpRequest request = new SignUpRequest("20214@gmail.com","password09","파일은 어떻게하냐");

        mvc.perform(post("/sushi/account")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    public void 회원가입_비밀번호_오류() throws Exception {
        SignUpRequest request = new SignUpRequest("20214@gmail.com","password","파일은 어떻게하냐");

        mvc.perform(post("/sushi/account")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 회원가입_비밀번호_길이오류() throws Exception {
        SignUpRequest request = new SignUpRequest("20214@gmail.com","1234","파일은 어떻게하냐");

        mvc.perform(post("/sushi/account")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(value = "201413lsy@dsm.hs.kr", password = "password1234")
    @Test
    public void 회원탈퇴() throws Exception {
        mvc.perform(delete("/sushi/account"))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "a", password = "a")
    @Test
    public void 회원탈퇴_실패() throws Exception {
        mvc.perform(delete("/sushi/account"))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(value = "201413lsy@dsm.hs.kr", password = "password1234")
    @Test
    public void 닉네임_수정() throws Exception {
        mvc.perform(put("/sushi/account")
                .param("name","hello")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(value = "201413lsy@dsm.hs.kr", password = "password1234")
    @Test
    public void 닉네임_수정_실패() throws Exception {
        mvc.perform(put("/sushi/account")
                .param("name","    ")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(value = "201413lsy@dsm.hs.kr", password = "password1234")
    @Test
    public void 닉네임_수정_실패2() throws Exception {
        mvc.perform(put("/sushi/account")
                .param("name","현재이곳은스터디카페입니다집에가고싶네요")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 닉네임_수정_로그인X() throws Exception {
        mvc.perform(put("/sushi/account")
                .param("name","hi")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void 프로필_정보() throws Exception {
        mvc.perform(get("/sushi/account?email=201413lsy@dsm.hs.kr"))
                .andExpect(status().isOk());
    }

    @Test
    public void 프로필_정보_못가져옴() throws Exception {
        mvc.perform(get("/sushi/account?email=20141@dsm.hs.kr"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void 로그인() throws Exception {
        SignInRequest signInRequest = new SignInRequest("yyuunn17@naver.com", "password1234");

        mvc.perform(post("/sushi/auth").content(new ObjectMapper().writeValueAsString(signInRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    public void 로그인_실패() throws Exception {
        SignInRequest signInRequest = new SignInRequest("yyu17@naver.com", "password1235");

        mvc.perform(post("/sushi/auth").content(new ObjectMapper().writeValueAsString(signInRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

}
