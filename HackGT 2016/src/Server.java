import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Will on 9/24/2016.
 */
public class Server {

    public ArrayList<Classroom> classes;
    public ArrayList<Professor> onlineProfs;
    public ArrayList<Student> onlineStudents;

    public Server() {
        this.classes = new ArrayList<Classroom>();
        this.onlineProfs = new ArrayList<Professor>();
        this.onlineStudents = new ArrayList<Student>();
    }

    public static void main(String[] args) throws IOException {
        Server s = new Server();
        int i = 1;
        System.out.println(new JSONObject().put("test", i));
        System.out.println(new JSONObject());
        System.out.println(s.request(new JSONObject().put("test", i)));
        System.out.println(s.request(new JSONObject()));
        while (true) {
            s.listenForAuth();
            try {
                Thread.sleep(4000); // One update per second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    public void direct(JSONObject message) {
        //handle the messages that are recieved
    }

    public void listenForAuth() {
        JSONObject response = this.request(new JSONObject().put("authenticate request?", ""));
        if (!response.has("type")) {
            System.out.println("No login requests!");
            return;
        }
        else if (response.get("type").equals("professor")) {
            this.logOnProf((String) response.get("name"), (int) response.get("id"));
        }
        this.logOnStudent((String) response.get("name"), (int) response.get("id"));
    }

    public JSONObject request(JSONObject j) {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://localhost:5000/peekaboo");
        StringEntity params = new StringEntity(j.toString(), ContentType.APPLICATION_JSON);
        httppost.setEntity(params);
        httppost.setHeader("Accept", "application/json");
        org.apache.http.HttpResponse resp = null;
        String responseString = "";
        try {
            resp = httpclient.execute(httppost);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((resp.getEntity().getContent())));
            String output;
            while ((output = br.readLine()) != null) {
                responseString += output;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpclient.getConnectionManager().shutdown();
        try {
            return new JSONObject(responseString);
        }
        catch (org.json.JSONException exp) {
            System.out.println("failed connection");
            System.out.println(responseString);
            return null;
        }
    }
}
