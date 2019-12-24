package com.example.finalltest;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class researchActivity  extends AppCompatActivity {
    private SearchView mSearchView;
    private ListView  contenList;
    private String [] mList;
    private  String namestr;
    private String  stat_plan_time,username;
    private int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suosuolayout);
        init();
        ArrayList<String> name=new ArrayList<>();
        ArrayList<String> start_plan_time=new ArrayList<>();/* 按照计划开始时间*/
        itemService iService =new itemService(researchActivity.this);
        name=iService.search_start_plan_time();
        item_userService iuService=new item_userService(researchActivity.this);
        start_plan_time=iuService.search_username();
        name.addAll(start_plan_time);
        name.toArray(new String[name.size()]);
        contenList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,name));
        contenList.setTextFilterEnabled(true);
        //设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            //当搜索内容改变时触发方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if(!TextUtils.isEmpty(newText)){
                    contenList.setFilterText(newText);
                }
                else{
                    contenList.clearTextFilter();
                }
                return false;
            }
        });
        final ArrayList<String> finalName = name;
        final ArrayList<String> finalStart_plan_time = start_plan_time;
        contenList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 namestr= finalName.get(position);
                /**
                 * 区别是名字还是日期
                 *
                 */
                for(int i = 0; i< finalStart_plan_time.size(); i++){
                    if(namestr.equals(finalStart_plan_time.get(i))){
                        flag=1;
                        break;
                    }else{
                       flag=0;
                    }
                }
                Intent intent1=new Intent(researchActivity.this,proFragmentActivity.class);
                intent1.putExtra("flag",String.valueOf(flag));
                intent1.putExtra("namestr",namestr);
                startActivity(intent1);

            }
        });
    }
    public void init(){
        mSearchView=(SearchView)findViewById(R.id.searchView);
        contenList=(ListView)findViewById(R.id.listView);

    }

}
