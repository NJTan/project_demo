package com.example.finalltest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class proFragmentActivity extends AppCompatActivity implements StarListFragment.Callbacks{
    private  int[] ino={};
    private String namestr;
    private int flag;
    item_detailService idService =new item_detailService(proFragmentActivity.this);
    project_detail project_detail=new project_detail();
    ArrayList<project_detail> project = new ArrayList<>();
    StartContent project_content=new StartContent();
    private String mangername;
    ArrayList<String> mangerList=new ArrayList<>();
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_two);
        Intent intent =getIntent();
        namestr=intent.getStringExtra("namestr");
        String e=intent.getStringExtra("flag");
        flag=Integer.parseInt(e);
      Log.d("tag12",namestr+flag);
      if(flag==1) project=idService.get_pro_list(namestr);
      else project=idService.get_pro(namestr);
        /***
         * 清除按了返回键重新进入的数据
         */
        if(!project_content.ITEMS.isEmpty()) {
            project_content.ITEMS.clear();
            project_content.ITEM_MAP.clear();
        }
        item_userService iuService=new item_userService(proFragmentActivity.this);
//        project = idService.get_pro_list(curusername);
        for (int i = 0; i < project.size(); i++) {
            int ino = project.get(i).getIno();
            Log.d("tag12112", String.valueOf(ino));
            mangername=iuService.get_manager(ino);
            mangerList.add(mangername);
            project_content.addItem(new StartContent.Project(project.get(i).getIno(), project.get(i).getIstate(), project.get(i).getStart_real_time(), project.get(i).getEnd_real_time(),
                    project.get(i).getInumber(), project.get(i).getIname(), project.get(i).getBeizu(), project.get(i).getRecord_time(), project.get(i).getDescription(), project.get(i).getEnd_plan_time()
                    , project.get(i).getStart_plan_time(), project.get(i).getMemberNames()));


        }

    }

    @Override
    public void onItemSelectedListener(Integer ino) {
        Bundle bundle = new Bundle();
        bundle.putInt(StarDetailFragment.ITEM_ID, ino);
        bundle.putSerializable("mangerList",mangerList);
        projectDetailFragment  fragment = new projectDetailFragment();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.star_list_detail,fragment).commit();
    }

}