import org.json.JSONArray;
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
    private ArrayList<Integer> classmateIds;
    private ArrayList<Integer> currentIds;
    private ArrayList<Student> classmates;
    private HashMap<JSONObject, HashMap<Integer, JSONObject>> questions;
    private double attendanceTolerance;

    public Classroom(String name, Professor prof, Server host, double attendanceTolerance) {
        this.name = name;
        this.prof = prof;
        this.host = host;
        this.classmateIds = this.getClassList();
        this.currentIds = new ArrayList<Integer>();
        this.classmates = new ArrayList<Student>();
        this.questions = new HashMap<JSONObject, HashMap<Integer, JSONObject>>();
    }

    public void attemptJoin(Student s, ArrayList<Integer> nearbyIds) {
        if (!classmateIds.contains(s.getId())) {
            throw new IllegalArgumentException("main.Student not in class");
        }
        classmates.add(s);
        for (Integer id : nearbyIds) {
            if (!currentIds.contains(id) && classmateIds.contains(id)) {
                currentIds.add(id);
            }
        }
    }

  public JSONObject getRawAnswers(String question) {
    JSONObject response = this.host.request(new JSONObject().put("type", "question").put("content" , question));
    JSONObject r = new JSONObject();
      String rawData = "";
    JSONArray answers = (JSONArray) response.get("answers");
      for(int i = 0; i < answers.length(); i++) {
          rawData += answers.getString(i) + " ,";
      }
    r.put("data", rawData);
    return r;
  }

  public JSONObject getPercentCorrect(String question, String answer) {
      JSONObject response = this.host.request(new JSONObject().put("type", "question").put("content" , question));
      JSONObject r = new JSONObject();
      JSONArray answers = (JSONArray) response.get("answers");
      int correctAnswers = 0;
      for(int i = 0; i < answers.length(); i++) {
          if (answers.getString(i).equals(answer)) {
              correctAnswers++;
          }
      }
      r.put("data", ((double) correctAnswers)/answers.length());
      return r;
  }


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
                host.request(b);
            }
        }
        return true;
    }

    public ArrayList<Integer> getClassList() {
        ArrayList<Integer> idList = new ArrayList<Integer>();
        JSONObject list = host.request(new JSONObject().put("type", "classlist").put("name", this.name));
        for (Object o : list.keySet()) {
            idList.add((Integer) o);
        }
        return idList;
    }


}
