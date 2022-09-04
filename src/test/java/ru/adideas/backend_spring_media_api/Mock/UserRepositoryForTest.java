package ru.adideas.backend_spring_media_api.Mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import ru.adideas.backend_spring_media_api.Role.Role;
import ru.adideas.backend_spring_media_api.User.Repository.UserRepository;
import ru.adideas.backend_spring_media_api.User.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserRepositoryForTest implements UserRepository {
    private final ObjectMapper objectMapper;
    private Integer userHashTableLastIndex = 0;
    private final HashMap<String, Integer> userHashTableEmailIndex;
    private final HashMap<Integer, User> userHashTable;
    private final HashMap<Integer, ArrayList<Integer>> banned;
    private final HashMap<Integer, ArrayList<Integer>> friends;
    private final HashMap<Integer, ArrayList<Integer>> roles;

    public UserRepositoryForTest() {
        objectMapper = new ObjectMapper();
        userHashTable = new HashMap<Integer, User>();
        userHashTableEmailIndex = new HashMap<String, Integer>();
        banned = new HashMap<Integer, ArrayList<Integer>>();
        friends = new HashMap<Integer, ArrayList<Integer>>();
        roles = new HashMap<Integer, ArrayList<Integer>>();
    }

    @Override
    public Collection<User> findBanned(Integer id) {
        ArrayList<Integer> users = banned.get(id);
        if (users == null) {
            users = new ArrayList<Integer>();
        }
        return users
                .stream()
                .map(userHashTable::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Integer iCanAddUserInBan(Integer user_id, Integer ban_user_id) {
        ArrayList<Integer> users = banned.get(user_id);
        if (users == null) {
            users = new ArrayList<Integer>();
        }
        return Math.toIntExact(
                users.stream().filter(x -> Objects.equals(x, ban_user_id)).count()
        );
    }

    @Override
    public void addUserInBan(Integer user_id, Integer ban_user_id) {
        ArrayList<Integer> users = banned.get(user_id);
        if (users == null) {
            banned.put(user_id, new ArrayList<Integer>(){{
                add(ban_user_id);
            }});
        } else {
            users.add(ban_user_id);
            banned.put(user_id, users);
        }
    }

    @Override
    public void delUserInBan(Integer user_id, Integer ban_user_id) {
        ArrayList<Integer> users = banned.get(user_id);
        if (users != null) {
            banned.put(
                    user_id,
                    users.stream()
                            .filter(x -> !Objects.equals(x, ban_user_id))
                            .collect(Collectors.toCollection(ArrayList::new))
            );
        }
    }

    @Override
    public Collection<User> findFriends(Integer id) {
        ArrayList<Integer> users = friends.get(id);
        if (users == null) {
            users = new ArrayList<Integer>();
        }
        return users
                .stream()
                .map(userHashTable::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Integer iCanAddFriend(Integer user_id, Integer friend_id) {
        ArrayList<Integer> users = friends.get(user_id);
        if (users == null) {
            users = new ArrayList<Integer>();
        }
        return Math.toIntExact(
                users.stream().filter(x -> Objects.equals(x, friend_id)).count()
        );
    }

    @Override
    public void addUserInFriend(Integer user_id, Integer friend_id) {
        ArrayList<Integer> users = friends.get(user_id);
        if (users == null) {
            friends.put(user_id, new ArrayList<Integer>(){{
                add(friend_id);
            }});
        } else {
            users.add(friend_id);
            friends.put(user_id, users);
        }
    }

    @Override
    public void delUserInFriend(Integer user_id, Integer friend_id) {
        ArrayList<Integer> users = friends.get(user_id);
        if (users != null) {
            friends.put(
                    user_id,
                    users.stream()
                            .filter(x -> !Objects.equals(x, friend_id))
                            .collect(Collectors.toCollection(ArrayList::new))
            );
        }
    }

    @Override
    public Optional<User> find(Integer id) {
        User user = userHashTable.get(id);
        if (user == null) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Integer index = userHashTableEmailIndex.get(email);
        if (index == null) {
            return Optional.empty();
        }
        return Optional.of(userHashTable.get(index));
    }

    @Override
    public void make(String name, String email, String password) {
        try {
            JSONObject jsonObject = new JSONObject();
            userHashTableLastIndex++;
            jsonObject.put("id", userHashTableLastIndex);
            jsonObject.put("name", name);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            userHashTableEmailIndex.put(email, userHashTableLastIndex);
            userHashTable.put(
                    userHashTableLastIndex,
                    objectMapper.readValue(jsonObject.toString(), User.class)
            );
        } catch (Exception exception) {}
    }

    @Override
    public Integer count() {
        return userHashTableLastIndex;
    }

    @Override
    public Integer iCanAddRole(Integer user_id, Integer role_id) {
        ArrayList<Integer> users = roles.get(user_id);
        if (users == null) {
            users = new ArrayList<Integer>();
        }
        return Math.toIntExact(
                users.stream().filter(x -> Objects.equals(x, role_id)).count()
        );
    }

    @Override
    public void addUserRole(Integer user_id, Integer role_id) {
        ArrayList<Integer> users = roles.get(user_id);
        if (users == null) {
            roles.put(user_id, new ArrayList<Integer>(){{
                add(role_id);
            }});
        } else {
            users.add(role_id);
            roles.put(user_id, users);
        }
    }
}
