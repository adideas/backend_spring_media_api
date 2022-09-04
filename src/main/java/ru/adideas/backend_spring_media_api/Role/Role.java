package ru.adideas.backend_spring_media_api.Role;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import ru.adideas.backend_spring_media_api.User.User;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String granted;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;

    @Override
    public String getAuthority() {
        return this.getGranted();
    }

    public Integer getId() {
        return this.id;
    }

    public String getGranted() {
        return granted;
    }
}
