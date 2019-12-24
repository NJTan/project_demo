package com.example.finalltest;

import android.app.Fragment;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import  com.example.finalltest.StartContent;

import java.util.ArrayList;

public class StarDetailFragment extends Fragment {
    public static final String ITEM_ID = "item_id";
    StartContent.Project star;
    private  String curuser,userType;
   ArrayList<String> managerList=new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ITEM_ID)){
            star = StartContent.ITEM_MAP.get(getArguments().getInt(ITEM_ID));
            curuser=getArguments().getString("curuser");
            userType=getArguments().getString("userType");
            managerList=getArguments().getStringArrayList("mangerList");
            Log.d("当前用户以及用户类型：",curuser+userType+managerList.get(0));
        }
    }

    @Override
    //传入布局
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_start_detail, container, false);
        if (star!= null){
            TextView textView = (TextView) view.findViewById(R.id.star_name);
            textView.setText(star.Iname);
            TextView textView1 = (TextView) view.findViewById(R.id.star_desc);
            textView1.setText("成员人数："+String.valueOf(star.Inumber)+"\n\n"+"项目描述："+
                    star.description+"\n\n");
            textView1.append("记录时间："+star.record_time+"\n\n");
            textView1.append("计划开始时间："+star.start_plan_time+"\n\n");
            textView1.append("计划结束时间："+star.end_plan_time+"\n\n");
            textView1.append("项目进度："+star.istate+"\n\n");
            textView1.append("实际开始时间："+star.start_real_time+"\n\n");
            textView1.append("实际结束时间："+star.end_real_time+"\n\n");
            for(int i=0;i<star.Inumber;i++) {
                       if(star.memberNames[i].equals(managerList.get(0))){
                           textView1.append("负责人："+star.memberNames[i]+"\n");
                       }
                       else {
                           textView1.append("普通成员："+star.memberNames[i]+"\n");
                           Log.i("memberName",star.memberNames[i]+"\n");
                       }


                Log.i("memberName1",star.memberNames[i]);
            }
            textView1.append("\n备注：\n");
            for(int i=0;i<star.Inumber;i++){
                textView1.append(star.beizu[i]+"\n\n");
            }
//            TextView textView2=(TextView) view.findViewById(R.id.star_pro);
//            textView2.setText(star.description);
            textView1.setMovementMethod(new ScrollingMovementMethod());
        }
        return view;

    }

}
