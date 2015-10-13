package com.ll.wsn;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ll.view.SexangleImageViews;
import com.ll.view.SexangleViewGroup;

//        findViewById(R.id.btn_add).setOnClickListener((View.OnClickListener) this);
//        findViewById(R.id.btn_remove).setOnClickListener((View.OnClickListener) this);
//AppCompatActivity

public class MainActivity extends Activity {

    private SexangleViewGroup sexangleViewGroup;
    ViewBean viewBean;
    SexangleImageViews imageViews;
    private static final int ID=0x10000;
    private LayoutTransition mTransition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sexangleViewGroup = (SexangleViewGroup) findViewById(R.id.sexangleView);
        initView();
    }
    public void initView(){
        for(int i=0;i<7;i++){
            viewBean=new ViewBean();
            viewBean.setHome(i);
            viewBean.setColor(i);
            viewBean.setTextsize(24);
            viewBean.setTexts(setName(i));
            imageViews=new SexangleImageViews(this, viewBean);
            imageViews.setId(ID + i);
            imageViews.setOnSexangleImageClick(listener);
//            imageViews.setOnLongSexangleImageClick(longListener);
            sexangleViewGroup.addView(imageViews);

            mTransition = new LayoutTransition();
            mTransition.setAnimator(LayoutTransition.APPEARING, ( ObjectAnimator.ofFloat(this, "scaleX", 0, 1)));
            PropertyValuesHolder propertyValues1=PropertyValuesHolder.ofFloat("translationY",-300,0);
            PropertyValuesHolder propertyValues2=PropertyValuesHolder.ofFloat("alpha", 0.1f, 1);
            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this, propertyValues1,propertyValues2);
            animator.setDuration(mTransition.getDuration(LayoutTransition.APPEARING));
            mTransition.setAnimator(LayoutTransition.APPEARING, animator);

            mTransition.setAnimator(LayoutTransition.DISAPPEARING, (ObjectAnimator.ofFloat(this, "scaleY", 1f, 0f)));
            //	mTransition.setAnimator(LayoutTransition.CHANGING, ( ObjectAnimator.ofFloat(this, "scaleX", 1f, 0f)));
            sexangleViewGroup.setLayoutTransition(mTransition);
        }
    }
    SexangleImageViews.OnSexangleImageClickListener listener=new SexangleImageViews.OnSexangleImageClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case ID:
                    Toast.makeText(MainActivity.this, setName(0), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, DataInfo.class);
                    startActivity(intent);
                    break;
                case ID+1:
                    Toast.makeText(MainActivity.this,setName(1), Toast.LENGTH_SHORT).show();
                    break;
                case ID+2:
                    Toast.makeText(MainActivity.this,setName(2), Toast.LENGTH_SHORT).show();
                    break;
                case ID+3:
                    Toast.makeText(MainActivity.this,setName(3), Toast.LENGTH_SHORT).show();
                    break;
                case ID+4:
                    Toast.makeText(MainActivity.this,setName(4), Toast.LENGTH_SHORT).show();
                    break;
                case ID+5:
                    Toast.makeText(MainActivity.this,setName(5), Toast.LENGTH_SHORT).show();
                    break;
                case ID+6:
                    Toast.makeText(MainActivity.this,setName(6), Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    public String setName(int i){
        if(i==0){
            return "空气";
        }
        else if(i==1){
            return "水质";
        }
        else if(i==2){
            return "水产";
        }
        else if(i==3){
            return "个人";
        }
        else if(i==4){
            return "气象";
        }
        else if(i==5){
            return "预警";
        }
        else if(i==6){
            return "设置";
        }
        return "";
    }

    public class ViewBean{
        private int color;
        private float textsize;
        private int home;
        private String texts;
        public int getColor() {
            return color;
        }
        public void setColor(int color) {
            this.color = color;
        }
        public float getTextsize() {
            return textsize;
        }
        public void setTextsize(float textsize) {
            this.textsize = textsize;
        }
        public int getHome() {
            return home;
        }
        public void setHome(int home) {
            this.home = home;
        }
        public String getTexts() {
            return texts;
        }
        public void setTexts(String texts) {
            this.texts = texts;
        }
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
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
