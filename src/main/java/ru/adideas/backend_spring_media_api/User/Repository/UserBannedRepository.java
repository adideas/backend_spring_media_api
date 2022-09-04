package ru.adideas.backend_spring_media_api.User.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.adideas.backend_spring_media_api.User.User;

import java.util.Collection;

@Repository
public interface UserBannedRepository extends org.springframework.data.repository.Repository<User, Integer>
{
    @Query(value = """
            SELECT * FROM users
            WHERE id IN (SELECT ban_user_id FROM users_banned WHERE user_id = :user_id)
            """,
            nativeQuery = true
    )
    Collection<User> findBanned(@Param("user_id") Integer id);



    @Query(value = """
            SELECT COUNT(*) AS can FROM users_banned
            WHERE user_id = :user_id and ban_user_id = :ban_user_id
            """,
            nativeQuery = true
    )
    Integer iCanAddUserInBan(@Param("user_id") Integer userId, @Param("ban_user_id") Integer banUserId);

    @Query(value = """
            INSERT INTO users_banned (user_id, ban_user_id) VALUES (:user_id, :ban_user_id)
            """,
            nativeQuery = true
    )
    @Transactional
    @Modifying
    void addUserInBan(@Param("user_id") Integer userId, @Param("ban_user_id") Integer banUserId);

    @Query(value = """
            DELETE FROM users_banned WHERE user_id = :user_id AND ban_user_id = :ban_user_id
            """,
            nativeQuery = true
    )
    @Transactional
    @Modifying
    void delUserInBan(@Param("user_id") Integer userId, @Param("ban_user_id") Integer banUserId);
}
