package com.sushiblog.backendv2.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sushiblog.backendv2.entity.category.Category;
import com.sushiblog.backendv2.entity.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false, length = 15)
    private String nickname;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonManagedReference
    private List<Category> categories;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user") //mappedBy는 매핑된 엔티티의 필드를 적어주면 된다
    @JsonManagedReference
    private List<Post> posts;

    public void updateNickName(String nickname) {
        this.nickname = nickname;
    }

}
