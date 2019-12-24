package com.example.finalltest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListFragmentActivity extends AppCompatActivity implements StarListFragment.Callbacks{
    private  String curusername;
    private  String  userType;
    private  int[] ino={};
    private String mangername;
    ArrayList<String> mangerList=new ArrayList<>();
    item_detailService idService =new item_detailService(ListFragmentActivity.this);
    project_detail project_detail=new project_detail();
    ArrayList<project_detail> project = new ArrayList<>();
    StartContent project_content=new StartContent();
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_two);
        Intent intent =getIntent();
        curusername=intent.getStringExtra("username");
        userType=intent.getStringExtra("userType");
        Log.d("tag12",curusername+userType);
        /***
         * 清除按了返回键重新进入的数据
         */
      if(!project_content.ITEMS.isEmpty()) {
          project_content.ITEMS.clear();
          project_content.ITEM_MAP.clear();
      }
        item_userService iuService=new item_userService(ListFragmentActivity.this);

          project = idService.get_pro_list(curusername);
          for (int i = 0; i < project.size(); i++) {
              int ino = project.get(i).getIno();
              mangername=iuService.get_manager(ino);
              mangerList.add(mangername);
              Log.d("tag12112", String.valueOf(ino));
              project_content.addItem(new StartContent.Project(project.get(i).getIno(), project.get(i).getIstate(), project.get(i).getStart_real_time(), project.get(i).getEnd_real_time(),
                      project.get(i).getInumber(), project.get(i).getIname(), project.get(i).getBeizu(), project.get(i).getRecord_time(), project.get(i).getDescription(), project.get(i).getEnd_plan_time()
                      , project.get(i).getStart_plan_time(), project.get(i).getMemberNames()));


          }

    }

    @Override
    public void onItemSelectedListener(Integer ino) {
        Bundle bundle = new Bundle();
        bundle.putInt(StarDetailFragment.ITEM_ID, ino);
        bundle.putString("curuser",curusername);
        bundle.putString("userType",userType);
        bundle.putSerializable("mangerList",mangerList);
        StarDetailFragment fragment = new StarDetailFragment();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.star_list_detail,fragment).commit();
    }

}
