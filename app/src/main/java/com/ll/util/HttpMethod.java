package com.ll.util;

import android.util.Log;

import org.json.JSONObject;

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


    public String getDataByGet(String path) throws Exception {
        URL Url = new URL(path);
        Log.v("zl_debug", path);
        HttpURLConnection HttpConn = (HttpURLConnection) Url.openConnection();
        Log.v("zl_debug", "1");
        HttpConn.setRequestMethod("GET");
        Log.v("zl_debug", "2");
        HttpConn.setReadTimeout(5000);
        HttpConn.setDoOutput(false);
        HttpConn.setRequestProperty("Content-Type", "application/json");
//        OutputStream os = HttpConn.getOutputStream();
        Log.v("zl_debug", "is it here?");
        Log.v("zl_debug", String.valueOf(HttpConn.getResponseCode()));
        if (HttpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            Log.v("zl_debug", "ok");
            return "login_success";
        }
        Log.v("zl_debug", "sorry");
        return "login_failed";
    }
}
