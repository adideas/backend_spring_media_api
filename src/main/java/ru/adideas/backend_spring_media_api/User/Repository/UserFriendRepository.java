package ru.adideas.backend_spring_media_api.User.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.adideas.backend_spring_media_api.User.User;

import java.util.Collection;

@Repository
public interface UserFriendRepository extends org.springframework.data.repository.Repository<User, Integer> {

    @Query(value = """
            SELECT * FROM users WHERE id IN (
                SELECT A.friend_id FROM users_friends AS A
                LEFT JOIN users_banned AS B
                    ON (A.user_id = B.user_id AND A.friend_id = B.ban_user_id)
                    OR (A.user_id = B.ban_user_id AND A.friend_id = B.user_id)
                LEFT JOIN users_friends AS C
                    ON C.user_id = A.friend_id AND C.friend_id = A.user_id
                WHERE A.user_id = :user_id AND B.user_id IS NULL AND A.user_id = C.friend_id
            )
            """,
            nativeQuery = true
    )
    Collection<User> findFriends(@Param("user_id") Integer id);

    @Query(value = """
            SELECT SUM(
                (
                    SELECT COUNT(*) FROM users_friends
                    WHERE user_id = :user_id AND friend_id = :friend_id
                ) + (
                    SELECT COUNT(*) FROM users_banned
                    WHERE user_id = :user_id OR ban_user_id = :friend_id
                )
            ) AS can
            """,
            nativeQuery = true
    )
    Integer iCanAddFriend(@Param("user_id") Integer userId, @Param("friend_id") Integer friendId);

    @Query(value = """
            INSERT INTO users_friends (user_id, friend_id) VALUES (:user_id, :friend_id)
            """,
            nativeQuery = true
    )
    @Transactional
    @Modifying
    void addUserInFriend(@Param("user_id") Integer userId, @Param("friend_id") Integer friendId);

    @Query(value = """
            DELETE FROM users_friends WHERE user_id = :user_id AND friend_id = :friend_id
            """,
            nativeQuery = true
    )
    @Transactional
    @Modifying
    void delUserInFriend(@Param("user_id") Integer userId, @Param("friend_id") Integer friendId);
}
