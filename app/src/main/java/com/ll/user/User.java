package com.ll.user;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ll.wsn.MainActivity;
import com.ll.wsn.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User extends Activity {
    private ListView user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initUI();
        listener();
    }
    public void initUI(){
        user = (ListView)findViewById(R.id.user);
        String[] strs = new String[] {
                "头像", "姓名", "昵称", "账户名", "性别", "地区"
        };
//        user.setAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, strs));
        List<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "头像");
        map.put("info", "");
        contents.add(map);
        map = new HashMap<String, Object>();
        map.put("title", "姓名");
        map.put("info", "张三");
        contents.add(map);
        map = new HashMap<String, Object>();
        map.put("title", "昵称");
        map.put("info", "冬天的狼");
        contents.add(map);
        map = new HashMap<String, Object>();
        map.put("title", "账户名");
        map.put("info", "lady");
        contents.add(map);
        map = new HashMap<String, Object>();
        map.put("title", "性别");
        map.put("info", "男");
        contents.add(map);
        map = new HashMap<String, Object>();
        map.put("title", "地区");
        map.put("info", "北京海淀");
        contents.add(map);
        SimpleAdapter adapter = new SimpleAdapter(this, contents, R.layout.user_info_item,
                new String[]{"title", "info"},new int[]{R.id.title,R.id.info});
        user.setAdapter(adapter);
    }
    public void listener(){
        user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 5){
                    Intent myIntent = new Intent();
                    myIntent = new Intent(User.this, PlaceChoose.class);
                    startActivity(myIntent);
//                    User.this.finish();
                }
            }
        });
        Button ret = (Button)findViewById(R.id.btn_ret);
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent();
                myIntent = new Intent(User.this, MainActivity.class);
                startActivity(myIntent);
                User.this.finish();
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent myIntent = new Intent();
            myIntent = new Intent(User.this, MainActivity.class);
            startActivity(myIntent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
