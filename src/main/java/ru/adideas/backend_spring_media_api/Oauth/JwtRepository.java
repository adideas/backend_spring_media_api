package ru.adideas.backend_spring_media_api.Oauth;

import com.sun.istack.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRepository extends CrudRepository<JwtModel, Integer> {
    @Query(
            value = "SELECT COUNT(*) FROM jwt WHERE id = :id",
            nativeQuery = true
    )
    Integer exist(@NotNull @Param("id") Integer id);

    @Query(
            value = "SELECT user_id FROM jwt WHERE id = :id",
            nativeQuery = true
    )
    Integer getUser(@NotNull @Param("id") Integer id);
}
