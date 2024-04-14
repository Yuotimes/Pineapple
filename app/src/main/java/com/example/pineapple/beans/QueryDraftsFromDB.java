package com.example.pineapple.beans;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class QueryDraftsFromDB {
    public static List<Drafts> queryDraftsFromDB(Context context, String account){
        List<Drafts> draftsList = new ArrayList<>();
        MySqlite helper = new MySqlite(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("drafts", new String[]{"account","title", "essay","photo1","photo2","photo3","username","userhead"}, null, null, null, null, null);
        if (cursor != null && cursor.getCount()> 0){
            while (cursor.moveToNext()){
                if (cursor.getString(0).equals(account)){
                    Drafts draft = new Drafts();
                    String title = cursor.getString(1);
                    String essay = cursor.getString(2);
                    String photo1 = cursor.getString(3);
                    String photo2 = cursor.getString(4);
                    String photo3 = cursor.getString(5);
                    String username = cursor.getString(6);
                    String userhead = cursor.getString(7);

                    draft.setTitle(title);
                    draft.setEssay(essay);
                    draft.setPhoto1(photo1);
                    draft.setPhoto2(photo2);
                    draft.setPhoto3(photo3);
                    draft.setUsername(username);
                    draft.setUserhead(userhead);
                    draftsList.add(draft);
                }
            }

        }
        cursor.close();
        db.close();
        return draftsList;
    }
}
