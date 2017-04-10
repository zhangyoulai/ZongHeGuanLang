package com.example.melificent.testforsearchhistory;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by p on 2017/2/7.
 */

public class Search_Listview extends ListView {
    public Search_Listview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Search_Listview(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    public Search_Listview(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
