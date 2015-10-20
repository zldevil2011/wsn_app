package com.ll.user;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ll.wsn.R;

public class EditInfo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        initData();
    }
    public void initData(){
        Intent intent=getIntent();
        String operate_name = "test";
        operate_name = intent.getStringExtra("operate_name");
        TextView name = (TextView)findViewById(R.id.operate_name);
//        Log.v("zl_debug", operate_name);
//        name.setText(operate_name);
    }
}
