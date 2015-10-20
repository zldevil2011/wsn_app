package com.ll.wsn;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Temperature extends Activity {
    private String aim_url = "";
    private  TextView wea_time, wea_tem, wea_wea, wea_humidity, wea_cloud_speed, wea_week, wea_lunnar;
    private JSONArray jsonArray = new JSONArray();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        initData();
        listener();
    }
    public void initData(){
        Toast.makeText(Temperature.this, "获取数据中...", Toast.LENGTH_SHORT).show();
        wea_time = (TextView)findViewById(R.id.wea_time);
        wea_tem = (TextView)findViewById(R.id.wea_tem);
        wea_wea = (TextView)findViewById(R.id.wea_wea);
        wea_humidity = (TextView)findViewById(R.id.wea_humidity);
        wea_cloud_speed = (TextView)findViewById(R.id.wea_cloud_speed);
        wea_week = (TextView)findViewById(R.id.wea_week);
        wea_lunnar = (TextView)findViewById(R.id.wea_lunnar);
        aim_url = getString(R.string.IP) + ":8089/api/data/weather/";
        try {
            new Thread(runnable).start();
            Toast.makeText(Temperature.this, "获取成功", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(Temperature.this, "获取失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void listener(){
        Button ret = (Button)findViewById(R.id.btn_ret);
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent();
                myIntent = new Intent(Temperature.this, MainActivity.class);
                startActivity(myIntent);
                Temperature.this.finish();
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent myIntent = new Intent();
            myIntent = new Intent(Temperature.this, MainActivity.class);
            startActivity(myIntent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("zl_debug","请求结果-->" + val);
            JSONObject wea = null;
            try {
                wea = jsonArray.getJSONObject(0);
                wea_time.setText(wea.getString("time"));
                wea_tem.setText(wea.getString("tem")+ "℃");
                wea_wea.setText(wea.getString("wea"));
                wea_humidity.setText("湿度:" + wea.getString("humidity")+"%");
                wea_cloud_speed.setText(wea.getString("cloud_speed")+"m/s");
                wea_week.setText(wea.getString("week"));
                wea_lunnar.setText("阴历" + wea.getString("lunnar"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    Runnable runnable = new Runnable(){
        @Override
        public void run() {
//            Todo the request
            JSONArray json = null;
            try {
                json = tem_getDataByGet(aim_url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value",String.valueOf(json));
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };
    public JSONArray tem_getDataByGet(String path) throws Exception {
        Log.v("zl_debug", path);
        URL Url = new URL(path);
        HttpURLConnection HttpConn = (HttpURLConnection) Url.openConnection();
        HttpConn.setRequestMethod("GET");
        HttpConn.setReadTimeout(5000);

        if (HttpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStreamReader isr = new InputStreamReader(HttpConn.getInputStream());
            String content = "";
            int i;
            while ((i = isr.read()) != -1) {
                content = content + (char) i;
            }
            isr.close();
            JSONObject jsonObject = new JSONObject(content);
            jsonArray = new JSONArray(jsonObject.getString("weather"));
//            for(int j = 0; j < jsonArray.length(); ++j){
//                JSONObject tmp = null;
//                tmp = jsonArray.getJSONObject(j);
//                ph[j] = tmp.getDouble(data_type);
//                x_data[j] = tmp.getDouble("water_id");
//            }
            return jsonArray;
        }
        return null;
    }
}
