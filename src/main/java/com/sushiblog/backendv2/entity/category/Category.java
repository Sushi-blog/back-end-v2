package com.sushiblog.backendv2.entity.category;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sushiblog.backendv2.entity.post.Post;
import com.sushiblog.backendv2.entity.user.User;
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
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15) //@Column의 length는 글자수(영어든 한국어든 상관 X)
    private String name;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id") //fk 값 이름
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    @JsonManagedReference
    private List<Post> posts;

    public void updateName(String name) {
        this.name = name;
    }

}
