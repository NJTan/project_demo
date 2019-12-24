package com.example.finalltest;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class setProcessActivity extends AppCompatActivity implements View.OnClickListener{
    private  String curuser;
    private  String userType;
    private String Iname;
    private Button set_end_real_time,set_start_real_time,btn_setProcess;
    private TextView txt_start_real_time,txt_end_real_time;
    private Spinner set_iState;
    private  String start_plan_time,end_plan_time,start_real_time,end_real_time;
    private  String[] Istate;
    private String curState;
    private  int state;
    Calendar calendar=Calendar.getInstance(Locale.CANADA);
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setprocess);
        final Intent intent =getIntent();
        curuser=intent.getStringExtra("curuser");
        userType=intent.getStringExtra("userType");
        Iname=intent.getStringExtra("Iname");
        Log.i("TagsetProcess",Iname);
        init();
        /**
         * 设置下拉列表，并获取下拉列表的值
         */
        Istate=new String[]{"未开始","进行中","已结束"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Istate);
        set_iState.setAdapter(adapter);
        set_iState.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curState=Istate[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_setProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    /**
                     * 获取计划开始时间和计划结束时间
                     */

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    ArrayList<String> time=new ArrayList<>();
                    item_detailService idService =new item_detailService(setProcessActivity.this);
                    time=idService.get_end_start_plan_time(curuser,Iname);
                    start_plan_time=time.get(0);
                    end_plan_time=time.get(1);
                    //如果实际开始时间比计划结束时间还晚就报错
                    if(dateFormat.parse(start_real_time).getTime()>dateFormat.parse(end_plan_time).getTime()){
                         Toast.makeText(setProcessActivity.this,"实际开始时间不能设置比计划结束时间还晚,请返回详情页面查询",Toast.LENGTH_SHORT).show();
                    }else if(dateFormat.parse(start_real_time).getTime()<dateFormat.parse(start_plan_time).getTime()){
                        Toast.makeText(setProcessActivity.this,"实际结束时间不能设置比计划开始时间还早，请返回详情页面查询",Toast.LENGTH_SHORT).show();
                    }else {
                        if(curState.equals("未开始")){
                            state=0;
                        }else if(curState.equals("进行中")){
                            state=1;
                        }else if(curState.equals("已结束")){
                            state=2;
                        }
                         if(idService.set_start_End_time(state,Iname,start_real_time,end_real_time)) {
                             Toast.makeText(setProcessActivity.this,"设置进度成功",Toast.LENGTH_SHORT).show();
                             finish();
                         }

                    }


                }
                catch (Exception e){
                    Toast.makeText(setProcessActivity.this,"请重试",Toast.LENGTH_SHORT).show();
                }

            }

        });
    }




    public  void init(){
        set_iState=(Spinner)findViewById(R.id.processState);
        set_start_real_time=(Button)findViewById(R.id.btn_start_real_time);
        set_end_real_time=(Button)findViewById(R.id.btn_end_real_time);
        set_end_real_time.setOnClickListener(this);
        set_start_real_time.setOnClickListener(this);
        btn_setProcess=(Button)findViewById(R.id.set_process_content);
        txt_end_real_time=(TextView)findViewById(R.id.txt_end_real_time);
        txt_start_real_time=(TextView)findViewById(R.id.txt_start_real_time);
    }
    /**
     * 获取实际开始时间
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public void showDatePickerDialog1(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
//             tv.setText("您选择了：" + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                final String data = year+"-" +(monthOfYear+1) + "-" + dayOfMonth ;
                tv.setText(data);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ",Locale.getDefault());
                String d = df.format(new Date(year-1900,monthOfYear,dayOfMonth));
                start_real_time=d;
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , 1
                ,1).show();
    }
    /**
     * 获取实际结束时间
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public void showDatePickerDialog2(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
//             tv.setText("您选择了：" + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                final String data = year+"-" +(monthOfYear+1) + "-" + dayOfMonth ;
                tv.setText(data);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ",Locale.getDefault());
                String d = df.format(new Date(year-1900,monthOfYear,dayOfMonth));
               end_real_time=d;
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , 1
                ,1).show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_real_time:
                showDatePickerDialog1(this,  2, txt_start_real_time, calendar);
                break;
            case R.id.btn_end_real_time:
                showDatePickerDialog2(this,  2,txt_end_real_time, calendar);;
                break;
            default:
                break;
        }
    }
}

