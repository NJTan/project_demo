package com.example.finalltest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class add_pro_beizuActivity  extends AppCompatActivity {
    private String curuser;
    private String userType;
    private String Iname;
    private String beizu_str;
    private Spinner projectname_spinner;
    private EditText beizhu_edit;
    private Button summit,setProsess_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbeizupro);
        Intent intent=getIntent();
        curuser= intent.getStringExtra("username");
        userType= intent.getStringExtra("userType");
//       Log.d("tag","当前用户"+curusername);
        init();
        /**
         * 获取spinner的值
         */
        ArrayList<String> projectName=new ArrayList<>();
        item_userService iuService=new item_userService(add_pro_beizuActivity.this);
        projectName= iuService.search_proName(curuser);
        projectName.toArray(new String[projectName.size()]);
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,projectName);
        projectname_spinner.setAdapter(adapter);
        final ArrayList<String> finalProjectName = projectName;
        /**
         * 监听下拉列表
         */
        projectname_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Iname= finalProjectName.get(position);
                Log.i("tag121",Iname);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /**
         * 提交备注
         */
        summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    beizu_str = beizhu_edit.getText().toString().trim();
                    item_userService iuService = new item_userService(add_pro_beizuActivity.this);
                    if (iuService.search_beizu(curuser, beizu_str, Iname)) {
                        Toast.makeText(add_pro_beizuActivity.this, "添加备注成功", Toast.LENGTH_SHORT).show();
                        if (userType.equals("普通成员")) {
                            finish();
                        }
                    }
                    ;
                }catch (Exception e){
                    Toast.makeText(add_pro_beizuActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        /**
         * 设置进度
         */
        setProsess_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(userType.equals("普通成员")){
                        Toast.makeText(add_pro_beizuActivity.this, "您不是负责人，不能设置进度", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent  intent2=new Intent(add_pro_beizuActivity.this,setProcessActivity.class);
                        intent2.putExtra("curuser",curuser);
                        intent2.putExtra("userType",userType);
                        intent2.putExtra("Iname",Iname);
                        startActivity(intent2);

                    }
                }
                catch (Exception e){
                    Toast.makeText(add_pro_beizuActivity.this, "权限不够", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
    public  void init(){
        projectname_spinner=(Spinner)findViewById(R.id.spin_one);
        beizhu_edit=(EditText)findViewById(R.id.set_beizu);
        summit=(Button) findViewById(R.id.sumbit_bei);
        setProsess_btn=(Button)findViewById(R.id.set_process);
    }

}
