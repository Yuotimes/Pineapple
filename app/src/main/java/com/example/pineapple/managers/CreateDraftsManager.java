package com.example.pineapple.managers;

import android.content.Context;

import com.example.pineapple.beans.CreateDraftsDBOP;

public class CreateDraftsManager {
    public long createDrafts(Context context, String account , String title, String essay, String photo1,String photo2,String photo3,String username,String userhead) {
        CreateDraftsDBOP createDraftsDBOP = new CreateDraftsDBOP(context);
        long result = createDraftsDBOP.insertDraft(account,title,essay, photo1,photo2,photo3,username,userhead);
        return result;
    }

}
