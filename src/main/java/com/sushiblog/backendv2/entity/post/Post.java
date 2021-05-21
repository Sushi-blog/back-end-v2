package com.sushiblog.backendv2.entity.post;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sushiblog.backendv2.entity.category.Category;
import com.sushiblog.backendv2.entity.user.User;
import com.sushiblog.backendv2.usecase.dto.request.PostRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class) //이벤트가 일어날 때마다 특정 로직 실행
@Entity
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15)
    private String title;

    @Column(length = 1000)
    private String content;

    @CreatedDate
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd`T`hh:mm:ss") // 띄어쓰기로 인해서 값이 잘못 넘어올까봐 T를 쓰는데, T를 포맷에서 그냥 쓸수가 없어서 ``로 감싸준다.
    private LocalDateTime createdAt;

    @Column(length = 100)
    private String imagePath;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    public void update(PostRequest request, String filePath) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.imagePath = filePath;

    }

}
