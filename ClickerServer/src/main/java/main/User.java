package main;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import main.Server;

/**
 * Created by Will on 9/24/2016.
 */
public abstract class User {

  private Server host;
  private String name;
  private int id;
  private boolean authenticated;

  public User (Server host, String name, int id) {
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
    JsonObjectBuilder b = Json.createObjectBuilder();
    b.add("type", "authenticate");
    b.add("id", this.id);
    b.add("name", this.name);
    try {
      host.request(b.build());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
