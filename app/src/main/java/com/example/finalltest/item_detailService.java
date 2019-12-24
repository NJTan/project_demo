package com.example.finalltest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class item_detailService {
    private Item_detailDataBaseHelper dbHelper;

    public item_detailService(Context context) {
        dbHelper = new Item_detailDataBaseHelper(context);
    }

    public boolean saveItem_detail(Item_detail item_detail) {
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        SQLiteDatabase wdb = dbHelper.getWritableDatabase();
        this.dbHelper.onCreate(wdb);
        String sql = "insert into item_detail(ino,description)values(?,?)";
        Object obj[] = {item_detail.getIno(), item_detail.getDescription()};
        sdb.execSQL(sql, obj);
        return true;
    }

    /**
     * 查询当前用户的项目列表，返回一个String[]
     */
    public  ArrayList<project_detail> get_pro_list(String curusername) {
        int i = 0;
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        SQLiteDatabase wdb = dbHelper.getReadableDatabase();
        String sql = "select ifnull(0,' '), * from user_item join item on user_item.ino=item.ino join item_detail  on item.ino=item_detail.ino where username=?  ";
        Cursor cursor = sdb.rawQuery(sql, new String[]{curusername});
        //int[] pro_ino_list = new int[cursor.getCount()];

//        if (cursor.moveToFirst()) {
//             pro_name_list[i++]= cursor.getInt(cursor.getColumnIndex("item.ino"));
//        }
        Log.i("android1", String.valueOf(cursor.getCount()));
        ArrayList<project_detail> project = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                try {
                    project_detail pro_detail = new project_detail();
                    pro_detail.setIno(cursor.getInt(cursor.getColumnIndex("ino")));
                    pro_detail.setIname(cursor.getString(cursor.getColumnIndex("Iname")));
                    pro_detail.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    pro_detail.setRecord_time(cursor.getString(cursor.getColumnIndex("record_time")));
                    pro_detail.setInumber(cursor.getInt(cursor.getColumnIndex("Inumber")));
                    pro_detail.setStart_plan_time(cursor.getString(cursor.getColumnIndex("start_plan_time")));
                    pro_detail.setEnd_plan_time(cursor.getString(cursor.getColumnIndex("end_plan_time")));
                    int istate=cursor.getInt(cursor.getColumnIndex("Istate"));
                    String start_real_time=cursor.getString(cursor.getColumnIndex("start_real_time"));
                    String end_real_time =cursor.getString(cursor.getColumnIndex("end_real_time"));
                    switch (istate){
                        case 0:pro_detail.setIstate("未开始");
                               break;
                        case 1:pro_detail.setIstate("进行中");
                                break;
                        case 2: pro_detail.setIstate("已结束");
                                break;
                        default:break;
                    }
                    if(start_real_time.equals("0")) pro_detail.setStart_real_time("未设置");
                    else pro_detail.setStart_real_time(start_real_time);
                    if(end_real_time.equals("0")) pro_detail.setEnd_real_time("未设置");
                    else pro_detail.setEnd_real_time(end_real_time);
                    project.add(pro_detail);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            cursor.close();

        }

        for (i = 0; i < project.size(); i++) {
            int ino = project.get(i).getIno();
            String sql2 = "select  * from user_item  where ino=?  ";
            Cursor cursor1 = sdb.rawQuery(sql2, new String[]{String.valueOf(ino)});
            ArrayList<String> membersName=new ArrayList<>();
            ArrayList<String> beizuList=new ArrayList<>();
            if (cursor1 != null && cursor1.getCount() > 0) {
                while (cursor1.moveToNext()) {
//                    Log.d("tag121", String.valueOf(cursor1.getCount()));
                    String name=cursor1.getString(cursor1.getColumnIndex("username"));
                    String beizu=cursor1.getString(cursor1.getColumnIndex("beizhu"));
                    if(beizu.equals("0")){
                        beizuList.add(name+": 该成员很懒，什么也没留下！");
                    }
                    else {
                        beizuList.add(name+":"+beizu);
                    }

                    membersName.add(name);

                }
            }
            project.get(i).setMemberNames( membersName.toArray(new String[membersName.size()]));
            Log.d("name123", String.valueOf(membersName.size()));
            project.get(i).setBeizu(beizuList.toArray(new String[beizuList.size()]));
            cursor1.close();

        }
//        String [] content={"0"};
//        content=project.get(0).getMemberNames();
//       Log.d("tag",content[1]);
        return  project;
    }
    /**
     * 查询项目计划时间为指定的项目的明细
     */
    public  ArrayList<project_detail> get_pro(String start_plan_time) {
        int i = 0;
        ArrayList<String> nameList=new ArrayList<>();
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        SQLiteDatabase wdb = dbHelper.getReadableDatabase();
        String sql = "select distinct item.ino, Iname,description,record_time,Inumber,start_plan_time,end_plan_time,Istate,start_real_time,end_real_time from user_item join item on user_item.ino=item.ino join item_detail  on item.ino=item_detail.ino where start_plan_time=?  ";
        Cursor cursor = sdb.rawQuery(sql, new String[]{start_plan_time});
//        Log.i("android1", String.valueOf(cursor.getCount()));
        ArrayList<project_detail> project=new ArrayList<>();
            Log.i("计划开始时间项目列表：", String.valueOf(cursor.getCount()));
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    try {
                        project_detail pro_detail = new project_detail();
                        pro_detail.setIno(cursor.getInt(cursor.getColumnIndex("ino")));
                        pro_detail.setIname(cursor.getString(cursor.getColumnIndex("Iname")));
                        pro_detail.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                        pro_detail.setRecord_time(cursor.getString(cursor.getColumnIndex("record_time")));
                        pro_detail.setInumber(cursor.getInt(cursor.getColumnIndex("Inumber")));
                        pro_detail.setStart_plan_time(cursor.getString(cursor.getColumnIndex("start_plan_time")));
                        pro_detail.setEnd_plan_time(cursor.getString(cursor.getColumnIndex("end_plan_time")));
                        int istate = cursor.getInt(cursor.getColumnIndex("Istate"));
                        String start_real_time = cursor.getString(cursor.getColumnIndex("start_real_time"));
                        String end_real_time = cursor.getString(cursor.getColumnIndex("end_real_time"));
                        switch (istate) {
                            case 0:
                                pro_detail.setIstate("未开始");
                                break;
                            case 1:
                                pro_detail.setIstate("进行中");
                                break;
                            case 2:
                                pro_detail.setIstate("已结束");
                                break;
                            default:
                                break;
                        }
                        if (start_real_time.equals("0")) pro_detail.setStart_real_time("未设置");
                        else pro_detail.setStart_real_time(start_real_time);
                        if (end_real_time.equals("0")) pro_detail.setEnd_real_time("未设置");
                        else pro_detail.setEnd_real_time(end_real_time);
                        project.add(pro_detail);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                cursor.close();


        }
            for (i = 0; i < project.size(); i++) {
                int ino = project.get(i).getIno();
                String sql2 = "select  * from user_item  where ino=?  ";
                Cursor cursor1 = sdb.rawQuery(sql2, new String[]{String.valueOf(ino)});
                ArrayList<String> membersName = new ArrayList<>();
                ArrayList<String> beizuList = new ArrayList<>();
                if (cursor1 != null && cursor1.getCount() > 0) {
                    while (cursor1.moveToNext()) {
//                    Log.d("tag121", String.valueOf(cursor1.getCount()));
                        String name = cursor1.getString(cursor1.getColumnIndex("username"));
                        String beizu = cursor1.getString(cursor1.getColumnIndex("beizhu"));
                        if (beizu.equals("0")) {
                            beizuList.add(name + ": 该成员很懒，什么也没留下！");
                        } else {
                            beizuList.add(name + ":" + beizu);
                        }

                        membersName.add(name);

                    }
                }
                project.get(i).setMemberNames(membersName.toArray(new String[membersName.size()]));
                Log.d("name123", String.valueOf(membersName.size()));
                project.get(i).setBeizu(beizuList.toArray(new String[beizuList.size()]));
                cursor1.close();

            }

//        String [] content={"0"};
//        content=project.get(0).getMemberNames();
//       Log.d("tag",content[1]);
        return  project;
    }


    /**
     * 查询负责人的名字所属的项目名，以便删除
     */
    public ArrayList<String> get_Itemname(String curuser){
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        SQLiteDatabase wdb = dbHelper.getReadableDatabase();
        String sql="select * from item join user_item on item.ino=user_item.ino where username=?";
        Cursor cursor=sdb.rawQuery(sql,new String[]{curuser});
        ArrayList<String> proNameList=new ArrayList<>();
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()) {
                String Iname = cursor.getString(cursor.getColumnIndex("Iname"));
                proNameList.add(Iname);
            }
        }
        return proNameList;
    }
    /**
     * 查询某一项目的计划开始时间和计划结束时间
     */
    ArrayList<String> time=new ArrayList<>();
    public ArrayList<String> get_end_start_plan_time(String curuser,String Iname){
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        SQLiteDatabase wdb = dbHelper.getReadableDatabase();
        String sql="select * from user_item join item on user_item.ino=item.ino where username=? and Iname=?";
        Cursor cursor=sdb.rawQuery(sql,new String[]{curuser,Iname});

        if(cursor!=null&&cursor.getCount()>0){
            while (cursor.moveToNext()){
               String start_plan_time=cursor.getString(cursor.getColumnIndex("start_plan_time"));
               String end_plan_time=cursor.getString(cursor.getColumnIndex("end_plan_time"));
               time.add(start_plan_time);
               time.add(end_plan_time);

            }
            cursor.close();
        }
        return  time;

    }

    /**
     * 将实际开始时间、实际结束时间、进度存进数据库中
     * @param istate
     * @param Iname
     * @param start_real_time
     * @param end_real_time
     * @return
     */
    public  boolean set_start_End_time(int istate,String Iname,String start_real_time,String end_real_time){
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        SQLiteDatabase wdb = dbHelper.getReadableDatabase();
        /**
         * 查询当前项目的项目号
         */
        int ino = 0;
        String sql2="select * from item where Iname=?";
        Cursor cursor1=sdb.rawQuery(sql2,new String[]{Iname});
        if(cursor1!=null&&cursor1.getCount()>0){
            while (cursor1.moveToNext()){
                ino=cursor1.getInt(cursor1.getColumnIndex("ino")) ;
            }
            cursor1.close();
        }
        String sql="update item_detail set Istate=?,start_real_time=?,end_real_time=? where ino=? ";
        Object obj[]={String.valueOf(istate),start_real_time,end_real_time, String.valueOf(ino)};
        sdb.execSQL(sql,obj);
        return  true;
    }
}


