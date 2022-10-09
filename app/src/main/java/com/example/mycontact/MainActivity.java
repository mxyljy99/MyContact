package com.example.mycontact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.mycontact.database.MyOpenHelper;
import com.example.mycontact.database.contactdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button add;
    private Button telephone;
    private Button message;
    private Button contact;

    // 定义一个变量，来标识是否退出
    private void call()
    {
        try
        {
            Intent call_intent = new Intent(Intent.ACTION_CALL);
            call_intent.setData(Uri.parse("tel:15888350679"));
            startActivity(call_intent);
        }catch (SecurityException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    call();

                }
                else
                {
                    Toast.makeText(MainActivity.this,"您未被授予权限！",Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        myOpenHelper= new MyOpenHelper(this, "Mycontacts.db", null, 1);
//        db=myOpenHelper.getWritableDatabase();
        add=(Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_view=new Intent(MainActivity.this,AddView.class);
                add_view.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(add_view);

            }
        });
        message=(Button) findViewById(R.id.message);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent message=new Intent(MainActivity.this, Message.class);
                message.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(message);

            }
        });
        telephone=(Button) findViewById(R.id.telephone);
        telephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent telephone=new Intent(MainActivity.this, telephone.class);
                telephone.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(telephone);
            }
        });


//        ArrayAdapter<String> adapter_test=new ArrayAdapter<String>(
//                this, android.R.layout.simple_list_item_1,data_test
//        );
//        ListView lisview_test=(ListView) findViewById(R.id.contact_listview);
//        lisview_test.setAdapter(adapter_test);

            showContact();


    }

    @SuppressLint("Range")
    public void showContact()
    {
        List<contactdata> list=new ArrayList<>();
        MyOpenHelper helper = new MyOpenHelper(this, "Mycontacts.db", null, 1);//创建对象
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query("contacts", null, null, null, null, null, null);
//        int ID=0;
        if (c != null && c.getCount() >= 1) {
            while (c.moveToNext()) {
                ContentValues values=new ContentValues();
                        list.add(new contactdata(c.getString(c.getColumnIndex("_id")),c.getString(c.getColumnIndex("name")), c.getString(c.getColumnIndex("number")),
                        c.getString(c.getColumnIndex("workplace")), c.getString(c.getColumnIndex("qunzu")), c.getString(c.getColumnIndex("e_mail")), c.getString(c.getColumnIndex("music"))));

            }

            c.close();
            db.close();//关闭数据库
        }
        else
        {

            list.add(new contactdata("0","name","number","workplace","qunzu","e_mail","music"));

        }

        List<Map<String,Object>> listitem=new ArrayList<Map<String,Object>>();


        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("name", list.get(i).name);
            map.put("number", list.get(i).number);

//            map.put("workplace", list.get(i).toString());
//            map.put("qunzu", list.get(i).toString());
//            map.put("e_mail", list.get(i).toString());
//            map.put("music", list.get(i).toString());
            listitem.add(map);
        }


        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this
                , listitem
                , R.layout.fragment_one_item
                , new String[]{"name", "number"}
                , new int[]{R.id.item_name, R.id.item_number});

        ListView listView = (ListView) findViewById(R.id.contact_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {




            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                int index=i;//从0开始

                final String items[] = {"呼叫联系人", "发送短信", "查看信息", "移出群组","移动分组","删除联系人"};
                AlertDialog.Builder listDialog = new AlertDialog.Builder(MainActivity.this);
                listDialog.setIcon(R.drawable.ic_launcher_foreground).setTitle("选项")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface pDialogInterface, int pI) {
                                MyOpenHelper helper = new MyOpenHelper(MainActivity.this, "Mycontacts.db", null, 1);//创建对象
                                SQLiteDatabase db=helper.getWritableDatabase();
                                String name ="";
                                String number="";
                                String place="";
                                String email="";
                                String group="";
                                String music="";
                                Cursor cursor=db.query("contacts", null, null, null, null, null, null);
                                if(cursor.moveToFirst())
                                {
                                    do{
                                        if(Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id")))==index)
                                        {
                                            name=cursor.getString(cursor.getColumnIndex("name"));
                                            number=cursor.getString(cursor.getColumnIndex("number"));
                                            place=cursor.getString(cursor.getColumnIndex("workplace"));
                                            email=cursor.getString(cursor.getColumnIndex("e_mail"));
                                            group=cursor.getString(cursor.getColumnIndex("qunzu"));
                                            music=cursor.getString(cursor.getColumnIndex("music"));

                                            Toast.makeText(MainActivity.this,name+" "+number,Toast.LENGTH_SHORT).show();


                                        }
                                    }
                                    while(cursor.moveToNext());

                                }


                                switch (pI)
                                {
                                    case 0:{
                                        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                                        {
                                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);

                                        }
                                        else
                                        {
                                            try
                                            {
                                                Intent call_intent = new Intent(Intent.ACTION_CALL);
                                                call_intent.setData(Uri.parse("tel:"+number));
                                                startActivity(call_intent);
                                            }catch (SecurityException e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }
//




                                        break;

                                    }
                                    case 1: {
                                        Uri smsToUri = Uri.parse("smsto:");

                                        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);

                                        startActivity(intent);

//                                        if(cursor.moveToFirst())
//                                        {
//                                            do{
//                                                String id=cursor.getString(cursor.getColumnIndex("_id"));
//                                                Toast.makeText(MainActivity.this,id,Toast.LENGTH_SHORT).show();
////                                                if(Integer.parseInt(id)==index)
////                                                {
////                                                    Toast.makeText(MainActivity.this,cursor.getString(cursor.getColumnIndex("_id")),Toast.LENGTH_SHORT).show();
////                                                    Toast.makeText(MainActivity.this,cursor.getString(cursor.getColumnIndex("name")),Toast.LENGTH_SHORT).show();
////
////                                                }
//                                            }while(cursor.moveToNext());
//
//
//                                        }
                                        break;
                                    }
                                    //查看联系人信息
                                    case 2: {

                                        Intent person_info=new Intent(MainActivity.this,personInfo.class);
                                        person_info.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        person_info.putExtra("name",name);
                                        person_info.putExtra("place",place);
                                        person_info.putExtra("number",number);
                                        person_info.putExtra("email",email);
                                        person_info.putExtra("group",group);
                                        person_info.putExtra("music",music);
                                        startActivity(person_info);


                                        break;
                                    }
                                    case 3:Toast.makeText(MainActivity.this, pI+"a", Toast.LENGTH_SHORT).show();break;
                                    case 4:Toast.makeText(MainActivity.this, pI+"a", Toast.LENGTH_SHORT).show();break;
                                    case 5:{
                                        Toast.makeText(MainActivity.this, index+"", Toast.LENGTH_SHORT).show();

                                        cursor=db.query("contacts", null, null, null, null, null, null);
                                        if(cursor.moveToFirst())
                                        {
                                            do{
                                                String id=cursor.getString(cursor.getColumnIndex("_id"));
                                                int ID=Integer.parseInt(id);
                                                if(ID==index)
                                                {
                                                    db.delete("contacts","_id=?",new String[]{String.valueOf(index)});


                                                }
                                                if(ID>index)//index是要删除的索引值
                                                {
                                                    ID--;
                                                    ContentValues values = new ContentValues();

                                                    values.put("_id", String.valueOf(ID));
                                                    db.update("contacts",values,"_id=?",new String[]{String.valueOf(ID+1)});

                                                }

                                            }while(cursor.moveToNext());

                                        }

//                                        db.delete("contacts","id=?",new String[] {""+(i-1)});
                                        adapter.notifyDataSetChanged();
                                        break;
                                    }
                                    default:break;


                                }
                            }
                        });
                listDialog.show();


            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//设置监听器
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
//                Toast.makeText(this, map.get("category").toString(), Toast.LENGTH_LONG).show();
//            }
//        });



    }









}