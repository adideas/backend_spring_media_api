package ru.adideas.backend_spring_media_api.News;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.adideas.backend_spring_media_api.User.User;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = User.class, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(columnDefinition="TEXT")
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @JsonProperty("created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("updated_at")
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public User getUser() {
        return this.user;
    }
}
