package com.ll.wsn;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DataInfo extends Activity {
    private static final String[] m={"PM2.5","风速", "雨量", "紫外线指数", "光量子", "风向"};
    private static final String[] m_url={"/air/?air_type=pm25","/air/?air_type=cloud",
            "/air/?air_type=rain", "/air/?air_type=ziwai", "/air/?air_type=guang", "/air/?air_type=clouddir"};
    private TextView view;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_info);
        initView();
    }
    public void initView(){
        view = (TextView) findViewById(R.id.spinnerText);
        spinner = (Spinner) findViewById(R.id.data_type_choice);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, m);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        spinner.setVisibility(View.VISIBLE);

        view.setText("选择数据类型：");
        web = (WebView)findViewById(R.id.wsn_web);
        WebSettings web_settings = web.getSettings();
        web_settings.setJavaScriptEnabled(true);
        web.loadUrl(getString(R.string.IP) + ":8070/air/");
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });
    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            web.loadUrl( getString(R.string.IP) + ":8070/" + m_url[arg2]);
        }
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }
}
