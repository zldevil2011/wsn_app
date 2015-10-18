package com.ll.user;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.ll.wsn.MainActivity;
import com.ll.wsn.R;

public class User extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initUI();
        listener();
    }
    public void initUI(){
        ListView user = (ListView)findViewById(R.id.user);
        String[] strs = new String[] {
                "头像", "姓名", "昵称", "账户名", "性别", "地区"
        };
        user.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strs));
    }
    public void listener(){
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
