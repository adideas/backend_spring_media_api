package ru.adideas.backend_spring_media_api.Comment;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.adideas.backend_spring_media_api.News.News;
import ru.adideas.backend_spring_media_api.User.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = News.class, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private News news;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = User.class, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(columnDefinition="TEXT")
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return this.user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
