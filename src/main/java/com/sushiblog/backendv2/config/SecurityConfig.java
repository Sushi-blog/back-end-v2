package com.sushiblog.backendv2.config;

import com.sushiblog.backendv2.security.JwtConfigure;
import com.sushiblog.backendv2.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .csrf().disable() //웹사이트 취약점 공격
                .cors().and()
                .sessionManagement().disable();
        http.authorizeRequests()
                .antMatchers("/sushi/account").permitAll()
                .antMatchers("/sushi/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/sushi/blog/**").permitAll()
                .antMatchers(HttpMethod.GET, "/sushi/blog/details/**").permitAll()
                .antMatchers(HttpMethod.GET, "/sushi/category/**").permitAll()
                .antMatchers(HttpMethod.GET, "/sushi/blog/file/**").permitAll()
                .and()
                .apply(new JwtConfigure(jwtTokenProvider));
    }

    @Override //implements WebMvcConfigurer
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //cors를 적용할 url 패턴. 와일드 카드 사용
                .allowedOrigins("*") // 자원 공유를 허용할 Origin / base url이라고 생각하면 될 듯
                .allowedMethods("*"); //자원 공유를 허용할 메소드
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
