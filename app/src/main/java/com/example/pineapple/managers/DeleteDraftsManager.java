package com.example.pineapple.managers;

import android.content.Context;

import com.example.pineapple.beans.DeleteDraftsDB;

public class DeleteDraftsManager {
    public void deleteDrafts(Context context,String account){
        DeleteDraftsDB deleteDraftsDB = new DeleteDraftsDB();
        deleteDraftsDB.deleteDraftsFormDB(context,account);
    }
}
