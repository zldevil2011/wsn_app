package com.ll.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import com.ll.wsn.R;

public class About_us extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initUI();
        listener();
    }
    public void initUI(){
        WebView web = (WebView)findViewById(R.id.about_us_browser);
        web.loadUrl(getString(R.string.IP) + ":8089/api/about_us");
    }
    public void listener(){
        Button ret = (Button)findViewById(R.id.btn_ret);
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent();
                myIntent = new Intent(About_us.this, Settings.class);
                startActivity(myIntent);
                About_us.this.finish();
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent myIntent = new Intent();
            myIntent = new Intent(About_us.this, Settings.class);
            startActivity(myIntent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
