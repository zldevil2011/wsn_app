package com.ll.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by dell on 2015/10/15.
 */
public class HttpMethod {

    public String submitDataByDoPost(String u, String p, Map<String, String> map, String path) throws Exception {
        URL Url = new URL(path);
        JSONObject user_info = new JSONObject();
        user_info.put("username", u);
        user_info.put("password", p);
        String content = String.valueOf(user_info);
        HttpURLConnection HttpConn = (HttpURLConnection) Url.openConnection();
        HttpConn.setRequestMethod("POST");
        HttpConn.setReadTimeout(5000);
        HttpConn.setDoOutput(true);
        HttpConn.setRequestProperty("Content-Type", "application/json");
//        HttpConn.setRequestProperty("Content-Length", String.valueOf(str.getBytes().length));
        OutputStream os = HttpConn.getOutputStream();
        os.write(content.getBytes());
        if (HttpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return "login_success";
        }
        return "login_failed";
    }


    public JSONObject getDataByGet(String path) throws Exception {
        Log.v("zl_debug", path);
        URL Url = new URL(path);
        HttpURLConnection HttpConn = (HttpURLConnection) Url.openConnection();
        HttpConn.setRequestMethod("GET");
        HttpConn.setReadTimeout(5000);
        JSONArray jsonArray = new JSONArray();
        if (HttpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStreamReader isr = new InputStreamReader(HttpConn.getInputStream());
            String content = "";
            int i;
            while ((i = isr.read()) != -1) {
                content = content + (char) i;
            }
            isr.close();
            JSONObject jsonObject = new JSONObject(content);
            return jsonObject;
        }
        return null;
    }
}
