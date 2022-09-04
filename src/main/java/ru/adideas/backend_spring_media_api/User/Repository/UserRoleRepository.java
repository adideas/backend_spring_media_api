package ru.adideas.backend_spring_media_api.User.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.adideas.backend_spring_media_api.User.User;

public interface UserRoleRepository extends org.springframework.data.repository.Repository<User, Integer> {
    @Query(value = """
            SELECT COUNT(*) FROM users_roles
            WHERE user_id = :user_id AND role_id = :role_id
            """,
            nativeQuery = true
    )
    Integer iCanAddRole(@Param("user_id") Integer userId, @Param("role_id") Integer roleId);

    @Query(value = """
            INSERT INTO users_roles (user_id, role_id) VALUES (:user_id, :role_id)
            """,
            nativeQuery = true
    )
    @Transactional
    @Modifying
    void addUserRole(@Param("user_id") Integer userId, @Param("role_id") Integer roleId);
}
