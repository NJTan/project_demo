package com.example.finalltest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class itemService {
    private ItemDataBaseHelper dbHelper;
    public  itemService(Context context){
        dbHelper=new ItemDataBaseHelper(context);
    }
    public boolean InputItem(Item item){
        SQLiteDatabase sdb =dbHelper.getReadableDatabase();
        SQLiteDatabase wdb=dbHelper.getWritableDatabase();
//        this.dbHelper.onUpgrade(wdb,3,3);
        this.dbHelper.onCreate(wdb);
        String sql="insert into item(Iname,Inumber,start_plan_time,end_plan_time,record_time)values(?,?,?,?,?)";
        Object obj[]={item.getIname(),item.getInumber(),item.getStart_plan_time(),item.getEnd_plan_time(),item.getRecord_time()};
        sdb.execSQL(sql,obj);
        return true;
    }

    /**
     * 删除列表中的某一项，并且数据库要更新
     * @param curuser
     * @param Iname
     * @return
     */
    public boolean removeItem(String curuser,String Iname){
        /**
         * 找出项目名的ino
         */
        SQLiteDatabase sdb =dbHelper.getReadableDatabase();
        SQLiteDatabase wdb=dbHelper.getWritableDatabase();
        String sql="select * from item join user_item on item.ino=user_item.ino where username=? and Iname=?";
        Cursor cursor=sdb.rawQuery(sql,new String []{curuser,Iname});
        int ino = 0;
        if(cursor!=null&&cursor.getCount()>0){
            while (cursor.moveToNext()) {
                ino = cursor.getInt(cursor.getColumnIndex("ino"));
            }
        }
        String sql1="delete from item_detail where ino=? ";
        Object obj[]={ino};
        sdb.execSQL(sql1,obj);
        String sql2="delete from user_item where ino=?";
        sdb.execSQL(sql2,obj);
        String sql3="delete from item where ino=?";
        sdb.execSQL(sql3,obj);
        return true;
    }

    /***
     * 获得项目计划时间列表
     * @return
     */
    public  ArrayList<String> search_start_plan_time(){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String sql="select distinct start_plan_time from item order by  start_plan_time ASC ";
        Cursor cursor=sdb.rawQuery(sql,null);
        ArrayList<String> timeList=new ArrayList<>();
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                String time=cursor.getString(cursor.getColumnIndex("start_plan_time"));
                Log.d("计划开始时间：",time);
                timeList.add(time);
            }
            cursor.close();
        }
        return  timeList;
    }
}
