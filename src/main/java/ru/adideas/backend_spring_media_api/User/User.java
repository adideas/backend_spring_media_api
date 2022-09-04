package ru.adideas.backend_spring_media_api.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.adideas.backend_spring_media_api.Role.Role;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;

    @ManyToMany(
            fetch = FetchType.LAZY,
            targetEntity = Role.class,
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    table = "users",
                    nullable = false,
                    foreignKey = @ForeignKey(name = "FK_ROLE_USER")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id",
                    table = "roles",
                    nullable = false,
                    foreignKey = @ForeignKey(
                            name = "FK_USER_ROLE",
                            foreignKeyDefinition = "FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE"
                    )
            )
    )
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(
            fetch = FetchType.LAZY,
            targetEntity = User.class,
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
            name = "users_friends",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    table = "users",
                    nullable = false,
                    foreignKey = @ForeignKey(name = "FK_FRIEND_USER")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "friend_id",
                    referencedColumnName = "id",
                    table = "users",
                    nullable = false,
                    foreignKey = @ForeignKey(
                            name = "FK_USER_FRIEND",
                            foreignKeyDefinition = "FOREIGN KEY (friend_id) REFERENCES users(id) ON DELETE CASCADE"
                    )
            )
    )
    private Set<User> friends = new HashSet<>();

    @ManyToMany(
            fetch = FetchType.LAZY,
            targetEntity = User.class,
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
            name = "users_banned",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    table = "users",
                    nullable = false,
                    foreignKey = @ForeignKey(name = "FK_BAN_USER")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "ban_user_id",
                    referencedColumnName = "id",
                    table = "users",
                    nullable = false,
                    foreignKey = @ForeignKey(
                            name = "FK_USER_BAN",
                            foreignKeyDefinition = "FOREIGN KEY (ban_user_id) REFERENCES users(id) ON DELETE CASCADE"
                    )
            )
    )
    private Set<User> banned = new HashSet<>();

    /*
     * GETTERS AND SETTERS
     * GETTERS AND SETTERS
     * GETTERS AND SETTERS
     * GETTERS AND SETTERS
     * GETTERS AND SETTERS
     */

    public Integer getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    @JsonIgnore
    public Object getEmail() {
        return this.email;
    }
    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    public void setName(String name) {
        this.name = name;
    }
    @JsonProperty
    public void setEmail(String email) {
        this.email = email;
    }
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
