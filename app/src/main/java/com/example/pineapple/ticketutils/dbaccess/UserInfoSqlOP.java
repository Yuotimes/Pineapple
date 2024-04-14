package com.example.pineapple.ticketutils.dbaccess;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pineapple.ticketbeans.UserInfo;

public class UserInfoSqlOP {

    public final MySqliteHelper mySqliteHelper;

    public UserInfoSqlOP(Context context){
        mySqliteHelper = new MySqliteHelper(context);
    }


    public long insertUserInfo (String username,String account, String password){

        SQLiteDatabase db = mySqliteHelper.getReadableDatabase();
//        将初始值point设置为0，插入数据库中
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("account",account);
        values.put("pw",password);
        values.put("point","0");
        long result =  db.insert("userinfo", null, values);
        db.close();

        return result;

    }


    public long insertUserInfo (UserInfo info){
        SQLiteDatabase db = mySqliteHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("username",info.getTableName());
        values.put("account",info.getAccount());
        values.put("pw",info.getPwd());
        values.put("point",info.getPoint());
        long result =  db.insert("userinfo", null, values);
        db.close();

        return result;

    }

    public long updateUserInfo(UserInfo info){

        SQLiteDatabase db = mySqliteHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("username",info.getUsername());
        values.put("account",info.getAccount());
        values.put("pw",info.getPwd());
        values.put("point",info.getPoint());
        int result = db.update("userinfo", values, "_id=?", new String[]{info.getId() + ""});
        db.close();

        return result;
    }

    //查询用户
    @SuppressLint("Range")
    public UserInfo queryUser(Activity activity, String accountQ, String pwdQ) {
        //实例化数据库操作类
        MySqliteHelper mhelper=new MySqliteHelper(activity);
        //实例化数据库对象
        SQLiteDatabase db=mhelper.getWritableDatabase();

        UserInfo user = null;
        //将封装好的一行数据保存到数据库的USER_TABLE表中
        // 使用原生SQL语句进行查询，选择userinfo表中符合条件的记录，条件是账户名和密码与传入的参数匹配
        Cursor cursor =db.rawQuery("select * from userinfo where account=? and pw=? ",new String[]{accountQ,pwdQ});
        if (cursor.moveToNext()) {
            int id=cursor.getInt(cursor.getColumnIndex("_id"));
            String account=cursor.getString(cursor.getColumnIndex("account"));
            String pwd=cursor.getString(cursor.getColumnIndex("pw"));
            String point=cursor.getString(cursor.getColumnIndex("point"));
            String username=cursor.getString(cursor.getColumnIndex("username"));

            user = new UserInfo();
            user.setId(id);
            user.setUsername(username);
            user.setAccount(account);
            user.setPwd(pwd);
            user.setPoint(point);
        }

        return user;
    }

    //查询用户
    @SuppressLint("Range")
    public UserInfo queryUser(Activity activity, String accountQ) {
        //实例化数据库操作类
        MySqliteHelper mhelper=new MySqliteHelper(activity);
        //实例化数据库对象
        SQLiteDatabase db=mhelper.getWritableDatabase();

        UserInfo user = null;
        //将封装好的一行数据保存到数据库的USER_TABLE表中
        // 使用原生SQL语句进行查询，选择userinfo表中符合条件的记录，条件是账户名和密码与传入的参数匹配
        Cursor cursor =db.rawQuery("select * from userinfo where account=?",new String[]{accountQ});
        if (cursor.moveToNext()) {
            int id=cursor.getInt(cursor.getColumnIndex("_id"));
            String account=cursor.getString(cursor.getColumnIndex("account"));
            String pwd=cursor.getString(cursor.getColumnIndex("pw"));
            String point=cursor.getString(cursor.getColumnIndex("point"));
            String username=cursor.getString(cursor.getColumnIndex("username"));

            user = new UserInfo();
            user.setId(id);
            user.setUsername(username);
            user.setAccount(account);
            user.setPwd(pwd);
            user.setPoint(point);
        }

        return user;
    }

    //获取当前登录用户
    @SuppressLint("Range")
    public UserInfo getLoginUser(Activity activity) {
        SharedPreferences sp=activity.getSharedPreferences("UP",0);
        String account = sp.getString("account", "");
        String pw = sp.getString("pw", "");
        return queryUser(activity, account, pw);
    }
}
