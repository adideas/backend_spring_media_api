package ru.adideas.backend_spring_media_api.Oauth;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.adideas.backend_spring_media_api.User.User;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "jwt")
public class JwtModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_JWT_USER"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public JwtModel setUser(User user) {
        this.user = user;
        return this;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }
}
