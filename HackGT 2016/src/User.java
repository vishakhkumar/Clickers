import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;


/**
 * Created by Will on 9/24/2016.
 */
public abstract class User {

    private Server host;
    private String name;
    private int id;
    private boolean authenticated;

    public User(Server host, String name, int id) {
        this.host = host;
        this.name = name;
        this.id = id;
        this.authenticated = false;
    }

    public int getId() {
        return id;
    }

    public void authenticate() {
        //go to the server and make sure my name and id exist in the system
        //send authenticated signal json
        JSONObject b = new JSONObject();
        b.put("type", "authenticate");
        b.put("id", this.id);
        b.put("name", this.name);
        host.request(b);
    }


}
