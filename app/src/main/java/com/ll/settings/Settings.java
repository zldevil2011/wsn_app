package com.ll.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ll.user.PlaceChoose;
import com.ll.wsn.MainActivity;
import com.ll.wsn.R;

import org.w3c.dom.Text;

public class Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initUI();
        listener();
    }
    public void initUI(){
        TextView about_us = (TextView)findViewById(R.id.about_us);
        TextView check_update = (TextView)findViewById(R.id.check_update);
        TextView logout = (TextView)findViewById(R.id.logout);
        about_us.getBackground().setAlpha(100);
        check_update.getBackground().setAlpha(100);
        logout.getBackground().setAlpha(100);
    }
    public void listener(){
        Button ret = (Button)findViewById(R.id.btn_ret);
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent();
                myIntent = new Intent(Settings.this, MainActivity.class);
                startActivity(myIntent);
                Settings.this.finish();
            }
        });
        TextView about_us = (TextView)findViewById(R.id.about_us);
        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent();
                myIntent = new Intent(Settings.this, About_us.class);
                startActivity(myIntent);
                Settings.this.finish();
            }
        });
        TextView check_update = (TextView)findViewById(R.id.check_update);
        check_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Settings.this, "当前已是最新版本", Toast.LENGTH_SHORT).show();
            }
        });
        TextView logout = (TextView)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent();
                myIntent = new Intent(Settings.this, MainActivity.class);
                startActivity(myIntent);
                Settings.this.finish();
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent myIntent = new Intent();
            myIntent = new Intent(Settings.this, MainActivity.class);
            startActivity(myIntent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_settings, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
