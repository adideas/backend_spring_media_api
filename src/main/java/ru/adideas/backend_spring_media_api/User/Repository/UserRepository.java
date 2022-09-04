package ru.adideas.backend_spring_media_api.User.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.adideas.backend_spring_media_api.User.User;

import java.util.Optional;

@Repository
public interface UserRepository extends org.springframework.data.repository.Repository<User, Integer>, UserFriendRepository, UserBannedRepository, UserRoleRepository {
    @Query(value = """
            SELECT * FROM users WHERE id = :id
            """,
            nativeQuery = true
    )
    Optional<User> find(@Param("id") Integer id);

    @Query(value = """
            SELECT * FROM users WHERE email = :email
            """,
            nativeQuery = true
    )
    Optional<User> findByEmail(@Param("email") String email);

    @Query(value = """
            INSERT INTO users (name, email, password, created_at, updated_at)
            VALUES (:name, :email, :password, NOW(), NOW())
            """,
            nativeQuery = true
    )
    @Transactional
    @Modifying
    void make(@Param("name") String name, @Param("email") String email, @Param("password") String password);

    @Query(value = """
            SELECT COUNT(*) FROM users
            """,
            nativeQuery = true
    )
    Integer count();
}
