package com.ll.chart;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.ll.util.HttpMethod;
import com.ll.wsn.R;
import com.ll.util.HttpMethod;
import static android.graphics.Color.*;

public class PieChart extends Activity {

    private static final String[] m={"PH值","DO值", "浑浊度", "水位", "电导率"};
    private static final String[] m_url={"/water/?water_type=ph","/water/?water_type=do",
            "/water/?water_type=turbidity", "/water/?water_type=water_level", "/water/?water_type=conductivity"};
    private Spinner spinner;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        LinearLayout layout = (LinearLayout)findViewById(R.id.chart);
        spinner = (Spinner) findViewById(R.id.water_type_choice);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, m);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        spinner.setVisibility(View.VISIBLE);


        String[] titles = new String[] { "温度"};
        List<double[]> x = new ArrayList<double[]>();
        for (int i = 0; i < titles.length; i++) {
            x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
        }
        List<double[]> values = new ArrayList<double[]>();
        values.add(new double[] { 12.3, 12.5, 13.8, 16.8, 20.4, 24.4, 26.4, 26.1, 23.6, 20.3, 17.2, 13.9 });
        int[] colors = new int[] { BLUE };
        PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE};
        XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
        }
        setChartSettings(renderer, "Average temperature", "Month", "Temperature", 0, 13, 0, 30, LTGRAY, LTGRAY);
        renderer.setXLabels(12);
        renderer.setYLabels(10);
        renderer.setShowGrid(true);
        renderer.setXLabelsAlign(Align.RIGHT);
        renderer.setYLabelsAlign(Align.RIGHT);
//        renderer.setZoomButtonsVisible(true);
        renderer.setPanLimits(new double[]{0, 13, 0, 30});
        renderer.setZoomLimits(new double[]{0, 13, 0, 30});
        renderer.setApplyBackgroundColor(true);
        renderer.setBackgroundColor(Color.WHITE);
        renderer.setMarginsColor(WHITE);
        renderer.setShowLegend(false);
//        renderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
        View view = ChartFactory.getLineChartView(this, buildDataset(titles, x, values), renderer);

//        view.setBackgroundColor(GREEN);
        layout.addView(view);//setContentView(view);
    }


    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            String aim = getString(R.string.IP) + m_url[arg2];
            aim = "http://www.baidu.com";
//            Toast.makeText(PieChart.this, aim, Toast.LENGTH_SHORT).show();
            try {

                Log.v("zl_debug", "here");
//                String dataByGet = submitDataByDoPost("123/", "456", aim);
                String dataByGet = "tmp";

                new Thread(runnable).start();
                Log.v("zl_debug", dataByGet);
                Log.v("zl_debug", "nothere");
                Toast.makeText(PieChart.this, "fine", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(PieChart.this, "failed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("mylog","请求结果-->" + val);
        }
    };

    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            //
            // TODO: http request.
            //
            String res = "failed";
            try {
                res = getDataByGet("http://www.baidu.com");
            } catch (Exception e) {
                e.printStackTrace();
            }
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value",res);
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };

    private XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        setRenderer(renderer, colors, styles);
        return renderer;
    }

    private void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors, PointStyle[] styles) {
        renderer.setAxisTitleTextSize(36);
        renderer.setChartTitleTextSize(36);
        renderer.setLabelsTextSize(25);
        renderer.setLegendTextSize(25);
        renderer.setMargins(new int[]{30, 30, 10, 30});
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            r.setPointStyle(styles[i]);
            r.setDisplayChartValues(true);
            renderer.addSeriesRenderer(r);
        }
    }

    private void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle, String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor, int labelsColor) {
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
    }

    private XYMultipleSeriesDataset buildDataset(String[] titles, List<double[]> xValues, List<double[]> yValues) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        addXYSeries(dataset, titles, xValues, yValues, 0);
        return dataset;
    }

    private void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles, List<double[]> xValues, List<double[]> yValues, int scale) {
        int length = titles.length;
        for (int i = 0; i < length; i++) {
            XYSeries series = new XYSeries(titles[i], scale);
            double[] xV = xValues.get(i);
            double[] yV = yValues.get(i);
            int seriesLength = xV.length;
            for (int k = 0; k < seriesLength; k++) {
                series.add(xV[k], yV[k]);
            }
            dataset.addSeries(series);
        }
    }

    public String getDataByGet(String path) throws Exception {
        URL Url = new URL(path);
        Log.v("zl_debug", path);
        HttpURLConnection HttpConn = (HttpURLConnection) Url.openConnection();
        Log.v("zl_debug", "1");
        HttpConn.setRequestMethod("GET");
        Log.v("zl_debug", "2");
        HttpConn.setReadTimeout(5000);
//        HttpConn.setDoOutput(false);
//        HttpConn.setRequestProperty("Content-Type", "application/json");
//        OutputStream os = HttpConn.getOutputStream();
        Log.v("zl_debug", "is it here?");

//        Log.v("zl_debug", String.valueOf(HttpConn.getContentEncoding()));
        if (HttpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            Log.v("zl_debug", "ok");
            return "login_success";
        }
        Log.v("zl_debug", "sorry");
        return "login_failed";
    }

    public String submitDataByDoPost(String u, String p , String path) throws Exception {
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
}