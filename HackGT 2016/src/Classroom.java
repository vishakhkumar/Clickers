import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Will on 9/24/2016.
 */
public class Classroom {

    private Server host;
    private Professor prof;
    private String name;
    private ArrayList<String> classmateIds;
    private ArrayList<String> currentIds;
    private ArrayList<Student> classmates;
    private HashMap<JSONObject, HashMap<Integer, JSONObject>> questions;
    private double attendanceTolerance;

    public Classroom(String name, Professor prof, Server host, double attendanceTolerance) {
        this.name = name;
        this.prof = prof;
        this.host = host;
        this.classmateIds = this.getClassList();
        this.currentIds = new ArrayList<String>();
        this.classmates = new ArrayList<Student>();
        this.questions = new HashMap<JSONObject, HashMap<Integer, JSONObject>>();
    }

    public void attemptJoin(Student s, ArrayList<String> nearbyIds) {
        if (!classmateIds.contains(s.getId())) {
            throw new IllegalArgumentException("main.Student not in class");
        }
        classmates.add(s);
        for (String id : nearbyIds) {
            if (!currentIds.contains(id) && classmateIds.contains(id)) {
                currentIds.add(id);
            }
        }
    }

  /*public JsonObject getRawAnswers(JsonObject question) {
    //go online
    JsonObjectBuilder b = Json.createObjectBuilder();
    JsonObject answers = host.request(question);
    String rawData = "";
    for (JsonValue j : answers.values()) {
      rawData += j.toString() + ", ";
    }
    b.add("data", rawData);
    return b.build();
  }*/


    public boolean equals(String name) {
        return name.equals(this.name);
    }

    public boolean classAuthenticated() {
        boolean authenticated = (currentIds.size() > classmateIds.size() * attendanceTolerance);
        if (!authenticated) {
            return false;
        } else {
            for (Student s : classmates) {
                JSONObject b = new JSONObject();
                b.put("id", s.getId());
                b.put("random", s.getNewRandomId());
                try {
                    host.request(b);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public ArrayList<String> getClassList() {
        return null; //TODO
    }


}