package com.example.pineapple.ticketsourceprovider;

import android.content.Context;

import com.example.pineapple.sourcereator.ISource;

import java.io.IOException;
import java.io.InputStream;

public class AssetsStreamOP implements ISource {
    public AssetsStreamOP(Context context){
        this.context = context;
    }

    private Context context;
    @Override
    public InputStream getInputStream(String fileName) throws IOException {
        return context.getAssets().open(fileName);
    }
}

