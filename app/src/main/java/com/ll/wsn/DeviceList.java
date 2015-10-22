package com.ll.wsn;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ll.grid.ExtendableListView;
import com.ll.grid.StaggeredGridView;
import com.ll.util.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class DeviceList extends Activity {

    private StaggeredGridView mGridView;
    private String [] device_url = new String[100000];
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private Bitmap[] bitmap = new Bitmap[100000];
    private Bitmap tmp_bitmap;
    private int Count = 0;
    private String aim_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        mGridView = (StaggeredGridView) findViewById(R.id.grid_view);
        initData();
        for(int i = 0; i < bitmap.length; ++i){
            Log.v("url", bitmap[i] + "");
        }
        mGridView.setAdapter(new MyAdapter());
    }
    public void initData(){
        new Thread(runnable).start();
    }
    Runnable runnable = new Runnable(){
        @Override
        public void run() {
//            Todo the request
            HttpMethod http = new HttpMethod();
            jsonObject = null;
            String res = "failed";
            try {
                jsonObject = http.getDataByGet(getString(R.string.IP) + ":8089/api/data/device/");
                res = "success";
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
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("zl_debug","请求结果-->" + val);
            try {
                jsonArray = new JSONArray(jsonObject.getString("device"));
                Log.v("zl_debug", String.valueOf(jsonArray));
                for(int i = 0; i <jsonArray.length(); ++i){
                    JSONObject tmp = jsonArray.getJSONObject(i);
                    aim_url = tmp.getString("device_photo");
//                    new Thread(runnable_getUrl).start();
                    device_url[i] = tmp.getString("device_photo");
                    Log.v("zl_debug_device_url", device_url[i]);
                }
            } catch (JSONException e) {
                Log.v("zl_debug", "some error");
                e.printStackTrace();
            }
            int count = 0;
            try {
                count = Integer.parseInt(jsonObject.getString("count"));
                Log.v("zl_debug", count + "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return DATA.length;
        }

        @Override
        public Object getItem(int position) {
            return DATA[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
//                convertView = new TextView(DeviceList.this);
//                convertView = new ImageView(DeviceList.this);
                convertView = new LinearLayout(DeviceList.this);
                ExtendableListView.LayoutParams lp = new
                        ExtendableListView.LayoutParams(ExtendableListView.LayoutParams.WRAP_CONTENT,
                        ExtendableListView.LayoutParams.WRAP_CONTENT);
                convertView.setLayoutParams(lp);
            }
//            TextView view = (TextView) convertView;
//            view.setText(DATA[position]);
//            view.setBackgroundColor(COLOR[position % 5]);
//            view.setGravity(Gravity.BOTTOM);
//            view.setTextColor(Color.WHITE);
            ImageView image = new ImageView(DeviceList.this);
            TextView text = new TextView(DeviceList.this);
            LinearLayout view = (LinearLayout) convertView;
            view.setOrientation(LinearLayout.VERTICAL);
            view.setBackgroundColor(COLOR[position % 5]);
//            image.setImageBitmap(bitmap[position]);	//设置Bitmap
            image.setImageResource(DATA[position % 7]);
            text.setText("测试设备");
            view.addView(image);
            view.addView(text);
            ExtendableListView.LayoutParams lp = (ExtendableListView.LayoutParams) view.getLayoutParams();
//            lp.height = (int) (getPositionRatio(position) * 200);
            lp.height = 330;
            view.setLayoutParams(lp);
            return view;
        }

    }
    Runnable runnable_getUrl = new Runnable(){
        @Override
        public void run() {
//            Todo the request
            HttpMethod http = new HttpMethod();
            jsonObject = null;
            String res = "failed";
            bitmap[Count] = getHttpBitmap(aim_url);
            Count++;
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value",res);
            msg.setData(data);
            handler_getUrl.sendMessage(msg);
        }
    };
    Handler handler_getUrl = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("zl_debug","请求结果-->" + val);
        }
    };
    public static Bitmap getHttpBitmap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    private final Random mRandom = new Random();
    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }

    private static final int[] DATA = new int[]{
            R.drawable.icon_cx, R.drawable.icon_flight,
            R.drawable.icon_hbtx,R.drawable.icon_hotel, R.drawable.icon_setting,
            R.drawable.icon_user, R.drawable.icon_water,

            R.drawable.icon_cx, R.drawable.icon_flight,
            R.drawable.icon_hbtx,R.drawable.icon_hotel, R.drawable.icon_setting,
            R.drawable.icon_user, R.drawable.icon_water,

            R.drawable.icon_cx, R.drawable.icon_flight,
            R.drawable.icon_hbtx,R.drawable.icon_hotel, R.drawable.icon_setting,
            R.drawable.icon_user, R.drawable.icon_water
    };
//    private static final String[] DATA = new String[] {
//            "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
//            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale",
//            "Aisy Cendre", "Allgauer Emmentaler", "Alverca", "Ambert", "American Cheese",
//            "Ami du Chambertin", "Anejo Enchilado", "Anneau du Vic-Bilh", "Anthoriro", "Appenzell",
//            "Aragon", "Ardi Gasna", "Ardrahan", "Armenian String", "Aromes au Gene de Marc",
//            "Asadero", "Asiago", "Aubisque Pyrenees", "Autun", "Avaxtskyr", "Baby Swiss",
//            "Babybel", "Baguette Laonnaise", "Bakers", "Baladi", "Balaton", "Bandal", "Banon",
//            "Barry's Bay Cheddar", "Basing", "Basket Cheese", "Bath Cheese", "Bavarian Bergkase",
//            "Baylough", "Beaufort", "Beauvoorde"
//    };

    private static final int[] COLOR = new int[] {
            0xff33b5e5, 0xffaa66cc, 0xff99cc00, 0xffffbb33, 0xffff4444
    };

}
