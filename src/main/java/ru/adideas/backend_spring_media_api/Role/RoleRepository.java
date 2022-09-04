package ru.adideas.backend_spring_media_api.Role;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

@Repository
public interface RoleRepository extends org.springframework.data.repository.Repository<Role, Integer> {
    @Query(
            value = """
                    SELECT * FROM roles WHERE id IN (
                        SELECT role_id FROM users_roles WHERE user_id = :user_id
                    )
                    """,
            nativeQuery = true
    )
    Collection<Role> getRolesFromUserId(@Param("user_id") Integer userId);

    @Query(
            value = """
                    SELECT COUNT(*) FROM roles
                    """,
            nativeQuery = true
    )
    Integer count();

    @Query(
            value = """
                    INSERT INTO roles (id, granted, created_at, updated_at) VALUES (:id, :granted, NOW(), NOW())
                    """,
            nativeQuery = true
    )
    @Transactional
    @Modifying
    void create(@Param("id") Integer id, @Param("granted") String granted);
}
