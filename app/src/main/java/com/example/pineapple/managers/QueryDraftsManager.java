package com.example.pineapple.managers;

import android.content.Context;

import com.example.pineapple.beans.Drafts;
import com.example.pineapple.beans.QueryDraftsFromDB;

import java.util.List;

public class QueryDraftsManager {
    public List<Drafts> queryDraftsDB(Context context, String account){
        List<Drafts> draftsList = QueryDraftsFromDB.queryDraftsFromDB(context, account);
        return draftsList;

    }
}
