package com.example.melificent.xuweizongheguanlang.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.melificent.xuweizongheguanlang.Adapter.TaskAdapter;
import com.example.melificent.xuweizongheguanlang.Bean.Task;
import com.example.melificent.xuweizongheguanlang.R;
import com.example.melificent.xuweizongheguanlang.Utils.TimeFormatUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/1.
 * This Activity can show Msg from client ,in here for get Msg by simulation not from server,
 * set adapter for listview can show Msg ,task will contain history and now task.
 * show task format will be "task time"+"task body",by task time you can know whether it is now or not.
 *
 */

public class InspectionTaskActivity extends AppCompatActivity {
    @InjectView(R.id.inspection_listview)
    ListView listView;
    TaskAdapter adapter;
    List<Task> tasks = new ArrayList<>();
    String [] taskbodies = new String[]{
            "维修A段管廊照明设备","查看B管廊通风设备","查看C段管廊管线","F段管廊6号仓水泵损坏，请维修，并报告管廊积水液位","手动查看G段管廊的气体详情，报告气体超标情况","B段管廊发电机出现故障，不能正常供电，查看异常详情"
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inspection_task);
        ButterKnife.inject(this);
        initDataForListview();
        setAdapterForListview();
    }

    private void setAdapterForListview() {
        adapter = new TaskAdapter(tasks,InspectionTaskActivity.this);
        listView.setAdapter(adapter);
    }

    private void initDataForListview() {

        for (int i=0 ;i<6;i++){
            Date date = new Date();
            Log.i("CurrentTime", "currentTime: "+date);
            Log.i("ivalue", "ivalue: "+i);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE,-i);
            Log.i("calendarTime", "calendarTime: "+date.getTime());
            date = calendar.getTime();
            Log.i("newTime", "new time: "+date);
            String taskTime = TimeFormatUtils.formatDate(date);
            Log.i("TaskTime", "TaskTime: "+taskTime);
            tasks.add(new Task(taskTime+":",taskbodies[i]));
        }




    }
}
