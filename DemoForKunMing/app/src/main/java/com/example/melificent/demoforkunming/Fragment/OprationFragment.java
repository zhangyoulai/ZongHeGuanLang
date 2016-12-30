package com.example.melificent.demoforkunming.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.melificent.demoforkunming.Activity.JiezhiActivity;
import com.example.melificent.demoforkunming.Activity.MainActivity;
import com.example.melificent.demoforkunming.Activity.RiChang;
import com.example.melificent.demoforkunming.Activity.ZhuanxiangActivity;
import com.example.melificent.demoforkunming.R;

/**
 * Created by p on 2016/11/7.
 */
public class OprationFragment extends Fragment {
    Button button_richangxuncha;
    Button button_zhuanxiangjiancha;
    Button button_jiezhi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = View.inflate(getActivity(), R.layout.fragment_opration,null);

        button_richangxuncha = (Button) view.findViewById(R.id.button_richangxuncha);
        button_jiezhi = (Button) view.findViewById(R.id.zhongdianjiezhijiancha);
        button_zhuanxiangjiancha = (Button) view.findViewById(R.id.zhuanxiangjiancha);

            //通过按钮的点击实现跳转到不同的页面，分别进入不同的界面
        setOnClickListener();


        return view;
    }

    private void setOnClickListener() {
        button_richangxuncha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getActivity(), RiChang.class);
                startActivity(intent1);

            }
        });
        button_jiezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(),JiezhiActivity.class);
                startActivity(intent2);
            }
        });
        button_zhuanxiangjiancha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getActivity(),ZhuanxiangActivity.class);
                startActivity(intent3);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (requestCode==1){
          MainActivity mainActivity = (MainActivity) getActivity();
          mainActivity.gotoOprationFragment();
      }
    }
}
