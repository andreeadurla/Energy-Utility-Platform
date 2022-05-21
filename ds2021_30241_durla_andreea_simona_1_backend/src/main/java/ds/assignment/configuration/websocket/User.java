package ds.assignment.configuration.websocket;

import java.security.Principal;
import java.util.UUID;

public class User implements Principal {

    private String id;

    public User(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return id + "";
    }
}
