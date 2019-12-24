package com.example.finalltest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class item_userService {
    private Item_useDataBaseHelper dbHelper;
    public item_userService(Context context){
        dbHelper=new Item_useDataBaseHelper(context);
    }
    public boolean Inputitem_userService(Item_user item_user){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        SQLiteDatabase wdb=dbHelper.getWritableDatabase();
        this.dbHelper.onCreate(wdb);
        String sql="insert into user_item(username,ino)values(?,?)";
        Object obj[]={item_user.getUsername(),item_user.getIno()};
        sdb.execSQL(sql,obj);
        return true;
    }
    public boolean search_beizu(String curuser,String newbeizhu,String Iname){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
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

            /**
             * 查询备注信息
             */
            String sql1="select * from user_item where username=? and ino=?";
            String oldbeizu = "";
            Log.d("ino",String.valueOf(ino));
            Cursor cursor = sdb.rawQuery(sql1, new String[]{curuser, String.valueOf(ino)});
            Log.d("ino", String.valueOf(cursor.getCount()));
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    oldbeizu=cursor.getString(cursor.getColumnIndex("beizhu"));
                }
                cursor.close();
        }
       if(!oldbeizu.equals("0")){
           String temp=oldbeizu+"\n"+newbeizhu;
           newbeizhu=temp;
       }
//        SQLiteDatabase wdb=dbHelper.getWritableDatabase();
        String sql="update user_item set beizhu=? where username=? and ino=?";
        Object obj[]={newbeizhu,curuser,String.valueOf(ino)};
        sdb.execSQL(sql,obj);
        return true;
    }
    ArrayList<String> Iname=new ArrayList<>();
    public ArrayList<String> search_proName(String curuser){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String sql="select * from user_item join item on user_item.ino=item.ino where username=?";
        Cursor cursor=sdb.rawQuery(sql,new
                 String[]{curuser});
        String name;
        if(cursor!=null&&cursor.getCount()>0){
            while (cursor.moveToNext()){
               name=cursor.getString(cursor.getColumnIndex("Iname"));
                Iname.add(name);
            }
            cursor.close();
        }

        return Iname;
    }

    /**
     * 获得用户列表
     * @return
     */
    public  ArrayList<String> search_username(){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String sql="select distinct username from user_item ";
        Cursor cursor=sdb.rawQuery(sql,null);
        ArrayList<String> nameList=new ArrayList<>();
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                String name=cursor.getString(cursor.getColumnIndex("username"));
                nameList.add(name);
            }
            cursor.close();
        }
        return  nameList;
    }
    /**
     * 获取负责人姓名
     *
     */
    public String get_manager(int ino){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String sql="select distinct user_item.username from user_item join pro_user on user_item.username=pro_user.username where userType=? and ino=?";
        Cursor cursor=sdb.rawQuery(sql,new String[]{"负责人",String.valueOf(ino)});
        String managerName = "";
        if(cursor!=null&&cursor.getCount()>0){
            while (cursor.moveToNext()){
                managerName=cursor.getString(cursor.getColumnIndex("username"));
            }
            cursor.close();
        }
        return managerName;
    }
}
