package com.example.finalltest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import  com.example.finalltest.ListFragmentActivity;
/**
 *
 * Created by W on 2016/7/30.
 */
public class StartContent {
    //定义一个内部类
    public static class Project{
       public int Ino;
       public int Inumber;
       public String Iname;
       public String start_real_time;
       public String end_real_time;
       public String description;
       public   String[] beizu;
       public   String istate;
       public   String[] memberNames;
       public String start_plan_time,end_plan_time,record_time;

        public Project(int ino, String istate , String start_real_time, String end_real_time,
                       int Inumber, String Iname,String[] beizu, String record_time,String description, String end_plan_time,
                       String start_plan_time,   String[] memberNames) {
            this.Ino= ino;
            this.Iname=Iname;
            this.Inumber=Inumber;
            this.start_plan_time=start_plan_time;
            this.end_plan_time=end_plan_time;
            this.record_time=record_time;
            this.beizu=beizu;
            this.description=description;
            this.start_real_time=start_real_time;
            this.end_real_time=end_real_time;
            this.istate=istate;
            this.memberNames=memberNames;
        }

        @Override
        public String toString() {
            return Iname;
        }
    }

    public static List<Project> ITEMS = new ArrayList<Project>();

    public static Map<Integer, Project> ITEM_MAP = new HashMap<Integer, Project>();

//    static {
//        for(int )
////        addItem(new Star(1, "易烊千玺", "跳舞担当"));
////        addItem(new Star(2, "王源", "特别可爱"));
////        addItem(new Star(3, "王俊凯", "队长"));
//    }

    public static void addItem(Project project){
        ITEMS.add(project);
        ITEM_MAP.put(project.Ino, project);
    }
    public  static  void moveItem(Project project){
         ITEMS.remove(project);
        ITEM_MAP.clear();
    }
}