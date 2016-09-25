package main;

import javax.json.JsonObject;

import main.Classroom;

/**
 * Created by Will on 9/24/2016.
 */


public class Professor extends User {

  private Server host;
  private String name;
  private int id;
  private boolean authenticated;

  public Professor (Server host, String name, int id) {
    super(host, name, id);
  }

  public void startClass(String className, double attendanceTolerance) {
    host.addClass(new Classroom(className, this, host, attendanceTolerance));
  }

  public void publishRawQuestion(JsonObject j) {

  }

  public void authenticate() {
    //go to the server and make sure my name and id exist in the system
  }

}
