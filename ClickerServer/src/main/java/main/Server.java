package main;

import java.io.IOException;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;

import org.apache.http.client.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

/**
 * Created by Will on 9/24/2016.
 */
public  class Server {

  public ArrayList<Classroom> classes;
  public ArrayList<Professor> onlineProfs;
  public ArrayList<Student> onlineStudents;

  public Server() {
    this.classes = new ArrayList<Classroom>();
    this.onlineProfs = new ArrayList<Professor>();
    this.onlineStudents = new ArrayList<Student>();
  }

  public ArrayList<Classroom> getClasses() {
    return classes;
  }

  public void addClass(Classroom newClass) {
    classes.add(newClass);
  }

  public void logOnProf(String name, int id) {
    Professor p = new Professor(this, name, id);
    p.authenticate();
    onlineProfs.add(p);
  }

  public void logOnStudent(String name, int id) {
    Student s = new Student(this, name, id);
    s.authenticate();
    onlineStudents.add(s);
  }

  public void direct(JsonObject message) {
    //handle the messages that are recieved
  }

  public void listen() {

  }

  public static void main(String[] args) throws IOException {
    Server s = new Server();
    int i = 0;
    while (true) {
      System.out.println(s.request(Json.createObjectBuilder().add("Test", i).build()));
      i++;
      try {
        Thread.sleep(1000); // One update per second
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }


  public String request(JsonObject j) throws IOException {
    HttpClient httpclient = HttpClients.createDefault();
    HttpPost httppost = new HttpPost("localhost:5000");
    StringEntity params = new StringEntity(j.toString(), ContentType.APPLICATION_JSON);
    httppost.setEntity(params);
    org.apache.http.HttpResponse resp = httpclient.execute(httppost);
    return resp.toString();

  }

  public void send(JsonObject build) {
  }
}
