package com.ll.chart;

import android.content.Context;
import android.content.Intent;

/**
 * Created by dell on 2015/10/14.
 */
public interface AChartAbstract {

    /**
     * 获取一个当前类型图标的Intent实例
     */
    public Intent getIntent(Context context);
}