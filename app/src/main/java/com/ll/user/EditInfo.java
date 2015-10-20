package com.ll.user;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ll.wsn.MainActivity;
import com.ll.wsn.R;

public class EditInfo extends Activity {
    private TextView name;
    private EditText info;
    private String position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        initData();
        listener();
    }
    public void listener(){
        Button ret = (Button)findViewById(R.id.btn_ret);
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(EditInfo.this, User.class);
                startActivity(myIntent);
                EditInfo.this.finish();
            }
        });
        Button save = (Button)findViewById(R.id.btn_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(EditInfo.this, User.class);
                Log.v("zl_debug_postion", position);
                Log.v("zl_debug_info", String.valueOf(info.getText()));


                myIntent.putExtra("save_id", position);
                myIntent.putExtra("save_info", info.getText().toString());
                startActivity(myIntent);
                EditInfo.this.finish();
            }
        });
    }
    public void initData(){
        Intent intent=getIntent();
        String operate_name = "test", operate_info = "test";
        operate_name = intent.getStringExtra("operate_name");
        operate_info = intent.getStringExtra("operate_info");
        position = intent.getStringExtra("position");
        name = (TextView)findViewById(R.id.operate_name);
        info = (EditText)findViewById(R.id.edit_info);
//        Log.v("zl_debug", operate_name);
        name.setText("修改" + operate_name);
        info.setText(operate_info);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent myIntent = new Intent();
            myIntent = new Intent(EditInfo.this, User.class);
            startActivity(myIntent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
