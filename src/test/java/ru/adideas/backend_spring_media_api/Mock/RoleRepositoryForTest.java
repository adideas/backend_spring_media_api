package ru.adideas.backend_spring_media_api.Mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.adideas.backend_spring_media_api.Role.Role;
import ru.adideas.backend_spring_media_api.Role.RoleRepository;

import java.util.ArrayList;
import java.util.Collection;

public class RoleRepositoryForTest implements RoleRepository {

    private final ObjectMapper objectMapper;
    private final Collection<Role> collection;

    public RoleRepositoryForTest() {
        objectMapper = new ObjectMapper();
        collection = new ArrayList<Role>();
        this.create(1, "ADMIN");
        this.create(2, "MANAGER");
    }

    @Override
    public Collection<Role> getRolesFromUserId(Integer user_id) {
        return collection;
    }

    @Override
    public Integer count() {
        return collection.size();
    }

    @Override
    public void create(Integer id, String granted) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("granted", granted);


            collection.add(
                    objectMapper.readValue(jsonObject.toString(), Role.class)
            );
        } catch (Exception exception) {}
    }
}
