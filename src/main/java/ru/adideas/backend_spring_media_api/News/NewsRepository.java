package ru.adideas.backend_spring_media_api.News;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface NewsRepository extends
        org.springframework.data.repository.Repository<News, Integer> {

    @Query(
            value = """
                    SELECT * FROM news
                    WHERE user_id <> :user_id
                    AND user_id IN (
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
    Set<News> findNewsForMe(@Param("user_id") Integer user_id);

    @Query(
            value = """
                    SELECT news.id FROM news
                    WHERE user_id IN (
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
    Set<Integer> findIdNews(@Param("user_id") Integer user_id);

    @Query(
            value = """
                    SELECT * FROM news WHERE user_id = :user_id
                    """,
            nativeQuery = true
    )
    Set<News> findMyNews(@Param("user_id") Integer user_id);

    @Query(
            value = """
                    SELECT * FROM news
                    WHERE id = :news_id
                    AND user_id IN (
                        SELECT A.friend_id FROM users_friends AS A
                        LEFT JOIN users_banned AS B
                            ON (A.user_id = B.user_id AND A.friend_id = B.ban_user_id)
                            OR (A.user_id = B.ban_user_id AND A.friend_id = B.user_id)
                        LEFT JOIN users_friends AS C
                            ON C.user_id = A.friend_id AND C.friend_id = A.user_id
                        WHERE A.user_id = :user_id AND B.user_id IS NULL AND A.user_id = C.friend_id
                    ) LIMIT 1
                    """,
            nativeQuery = true
    )
    News find(@Param("news_id") Integer news_id, @Param("user_id") Integer user_id);

    @Query(
            value = """
                    INSERT INTO news (user_id, text, created_at, updated_at)
                    VALUES (:user_id, :text, NOW(), NOW())
                    """,
            nativeQuery = true
    )
    @Transactional
    @Modifying
    void make(@Param("user_id") Integer user_id, @Param("text") String text);

    @Query(
            value = """
                    INSERT INTO news (id, user_id, text, created_at, updated_at)
                    VALUES (:id, :user_id, :text, NOW(), NOW())
                    """,
            nativeQuery = true
    )
    @Transactional
    @Modifying
    void makeWithId(@Param("id") Integer id, @Param("user_id") Integer user_id, @Param("text") String text);

    @Query(
            value = """
                    SELECT COUNT(*) FROM news
                    """,
            nativeQuery = true
    )
    Integer count();
}
