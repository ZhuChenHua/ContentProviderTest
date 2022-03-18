package com.example.contentprovidertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.database.CursorWindowCompat;

import android.content.ContentValues;
import android.database.Cursor;
import android.icu.util.LocaleData;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * ContentProvider 内容提供器的用法一般有两种，一种是使用现有的内容提供器来读取和操作相应程序中的数据，另一种是创建自己的内容提供器给我们程序的数据提供外部访问接口。
 */
public class MainActivity extends AppCompatActivity {

    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addData = (Button)findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加数据
                Uri uri = Uri.parse("content://com.example.databasetest.provider/book");
                ContentValues contentValues = new ContentValues();
                contentValues.put("name","A Clash of Kings");
                contentValues.put("author","George Martin");
                contentValues.put("pages",1000);
                contentValues.put("price",77);
                newId = getContentResolver().insert(uri,contentValues).getPathSegments().get(1);
            }
        });
        Button queryData = (Button)findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //查询数据
                Uri uri = Uri.parse("content://com.example.databasetest.provider/book");
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                if (cursor != null){
                    while (cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity","book name is " + name);
                        Log.d("MainActivity","book author is " + author);
                        Log.d("MainActivity","book pages is " + pages);
                        Log.d("MainActivity","book price is " + price);
                    }
                    cursor.close();
                }
            }
        });
        Button updateData = (Button)findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //更新数据
                Uri uri = Uri.parse("content://com.example.databasetest.provider/book/" + newId);
                ContentValues contentValues = new ContentValues();
                contentValues.put("name","A Clash of Kings");
                contentValues.put("author","George Martin");
                contentValues.put("pages",1550);
                contentValues.put("price",99);
                getContentResolver().update(uri,contentValues,null,null);
            }
        });
        Button deleteData = (Button)findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除数据
                Uri uri = Uri.parse("content://com.example.databasetest.provider/book/" + newId);
                getContentResolver().delete(uri,null,null);
            }
        });
    }
}
