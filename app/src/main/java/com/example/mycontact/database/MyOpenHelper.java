package com.example.mycontact.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Mycontacts.db";
    public static final String TABLE_NAME="contacts";
    private Context mcontext;

    public static final String CREATE_TABLE="create table contacts("
            +"_id integer,"
            +"name varchar(10), "
            +"number varchar(15), "
            +"workplace varchar(20), "
            +"qunzu varchar(10), "
            +"e_mail varchar(20), "
            +"music varchar(20))";



    public MyOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建数据表
        sqLiteDatabase.execSQL(CREATE_TABLE);
        ContentValues values=new ContentValues();
        values.put("_id","0");
        values.put("name","马星宇");
        values.put("number","15888350679");
        values.put("workplace","宁波大学");
        values.put("qunzu","男");
        values.put("e_mail","296286412@qq.com");
        values.put("music","胆小鬼");

        sqLiteDatabase.insert(MyOpenHelper.TABLE_NAME,null,values);
//        System.out.println(values);
        Toast.makeText(mcontext,"数据库创建成功",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //更新数据表
    }
}
