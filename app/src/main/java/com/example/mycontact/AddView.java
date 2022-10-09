package com.example.mycontact;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycontact.database.MyOpenHelper;


public class AddView extends AppCompatActivity {
    private Button comfirm;
    private EditText mEtName;
    private EditText mEtPhone;
    private EditText mEtPlace;
    private EditText mEtemail;
    private  EditText mEtgroup;
    private EditText mEtmusic;
    private MyOpenHelper myOpenHelper;
    public SQLiteDatabase db;
    public int ID=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_view);
        comfirm=(Button) findViewById(R.id.confirm_button);
        myOpenHelper= new MyOpenHelper(this, "Mycontacts.db", null, 1);
        db=myOpenHelper.getWritableDatabase();
        mEtName=(EditText) findViewById(R.id.name_edit);
        mEtPlace=(EditText) findViewById(R.id.place_edit);
        mEtPhone=(EditText) findViewById(R.id.phone_edit);
        mEtemail=(EditText) findViewById(R.id.mail_edit);
        mEtgroup=(EditText) findViewById(R.id.group_edit);
        mEtmusic=(EditText) findViewById(R.id.music_edit);
        comfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String name=mEtName.getText().toString().trim();
                String place=mEtPlace.getText().toString().trim();
                String phone=mEtPhone.getText().toString().trim();
                String email=mEtemail.getText().toString().trim();
                String group=mEtgroup.getText().toString().trim();
                String music=mEtmusic.getText().toString().trim();

                MyOpenHelper helper = new MyOpenHelper(AddView.this, "Mycontacts.db", null, 1);//创建对象
                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor cursor=db.query("contacts",null,null,null,null,null,null);
                if(cursor!=null&&cursor.getCount()>=1)
                {
                    if(cursor.moveToLast())
                    {
                        @SuppressLint("Range") String _id=cursor.getString(cursor.getColumnIndex("_id"));
                        ID=Integer.valueOf(_id);
                        ID++;
                        _id=String.valueOf(ID);
                        insert(_id,name,place,phone,email,group,music);


                        Toast.makeText(AddView.this,_id+"",Toast.LENGTH_SHORT).show();


                    }
                }
                else{
                    insert("0",name,place,phone,email,group,music);
                    ID++;

                }

                    //        int ID=0;
//        if (c != null && c.getCount() >= 1) {
//            while (c.moveToNext()) {
//                ContentValues values=new ContentValues();
//                values.put("_id",String.valueOf(ID));
//
//
//                list.add(new contactdata(String.valueOf(ID),c.getString(c.getColumnIndex("name")), c.getString(c.getColumnIndex("number")),
//                        c.getString(c.getColumnIndex("workplace")), c.getString(c.getColumnIndex("qunzu")), c.getString(c.getColumnIndex("e_mail")), c.getString(c.getColumnIndex("music"))));
//                ID++;
//            }
//
//            c.close();
//            db.close();//关闭数据库
//        }
//        else
//        {
//            ID=0;
//            list.add(new contactdata("0","name","number","workplace","qunzu","e_mail","music"));
//
//        }






                Intent main_view=new Intent(AddView.this,MainActivity.class);
                main_view.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main_view);
            }


        });

    }

    public  void insert(String _id,String name,String place,String phone,String email,String group,String music)
    {

        db=myOpenHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("_id",_id);
        values.put("name",name);
        values.put("number",phone);
        values.put("workplace",place);
        values.put("qunzu",group);
        values.put("e_mail",email);
        values.put("music",music);
        db.insert(MyOpenHelper.TABLE_NAME,null,values);//参数1：表名，参数2:指定数据段名字，null为所有，参数3：数据
        Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
        db.close();
    }
}