package klik.klik;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Sayak on 9/25/2016.
 */
public class Utilities {
    public JSONObject request(JSONObject j) {
        String url="http://url.com";
        URL object= null;
        try {
            object = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) object.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        try {
            con.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        JSONObject cred   = new JSONObject();
        JSONObject auth   = new JSONObject();
        JSONObject parent = new JSONObject();

        try {
            cred.put("username","adm");
            cred.put("password", "pwd");

            auth.put("tenantName", "adm");
            auth.put("passwordCredentials", cred.toString());

            parent.put("auth", auth.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OutputStreamWriter wr = null;
        try {
            wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(parent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }



//display what returns the POST request

        StringBuilder sb = new StringBuilder();
        int HttpResult = 0;
        try {
            HttpResult = con.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                System.out.println("" + sb.toString());
            } else {
                System.out.println(con.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return new JSONObject(sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
