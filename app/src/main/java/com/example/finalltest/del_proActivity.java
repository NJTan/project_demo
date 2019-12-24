package com.example.finalltest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Map;

public class del_proActivity extends AppCompatActivity {
    private  String curname,userType;
    private String[] projectNAmeList;
    private String proName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.del_list);
        Intent intent=getIntent();
       curname= intent.getStringExtra("username");
        userType=intent.getStringExtra("userType");
        ArrayList<String> proNameList = new ArrayList<>();
        item_detailService idService =new item_detailService(del_proActivity.this);
         proNameList=idService.get_Itemname(curname);
        proNameList.toArray(new String[proNameList.size()]);
        ListView listView = (ListView) findViewById(R.id.listview);//在视图中找到ListView
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,proNameList);//新建并配置ArrayAapeter
        listView.setAdapter(adapter);
        final ArrayList<String> finalProNameList = proNameList;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               proName= finalProNameList.get(position);
               itemService iService =new itemService(del_proActivity.this);
               iService.removeItem(curname,proName);
                adapter.notifyDataSetChanged();
                Toast.makeText(del_proActivity.this,"删除完成",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}
