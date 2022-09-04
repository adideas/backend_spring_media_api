package ru.adideas.backend_spring_media_api.Mock;

import ru.adideas.backend_spring_media_api.Oauth.JwtModel;
import ru.adideas.backend_spring_media_api.Oauth.JwtRepository;
import ru.adideas.backend_spring_media_api.User.User;

import java.util.HashMap;
import java.util.Optional;

public class JwtRepositoryForTest implements JwtRepository {

    Integer lastIndex = 0;
    HashMap<Integer, User> users;
    HashMap<Integer, JwtModel> tokens;


    public JwtRepositoryForTest() {
        users = new HashMap<Integer, User>();
        tokens = new HashMap<Integer, JwtModel>();
    }

    @Override
    public Integer exist(Integer id) {
        return users.containsKey(id) ? 1 : 0;
    }

    @Override
    public Integer getUser(Integer id) {
        return users.get(id).getId();
    }

    @Override
    public <S extends JwtModel> S save(S jwtModel) {
        lastIndex++;
        users.put(lastIndex, jwtModel.getUser());
        jwtModel.setId(lastIndex);
        return jwtModel;
    }

    @Override
    public <S extends JwtModel> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Iterable<JwtModel> findAll() {
        return null;
    }

    @Override
    public Iterable<JwtModel> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public Optional<JwtModel> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }


    @Override
    public void delete(JwtModel entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends JwtModel> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
