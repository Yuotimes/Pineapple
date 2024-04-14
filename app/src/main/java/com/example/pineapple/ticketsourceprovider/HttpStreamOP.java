package com.example.pineapple.ticketsourceprovider;



import com.example.pineapple.ticketexceptions.ErrorResponseCodeException;
import com.example.pineapple.sourcereator.ISource;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpStreamOP implements ISource {

    @Override
    public InputStream getInputStream(String url) throws IOException, ErrorResponseCodeException {
        InputStream in = null;
        URL mUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) mUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        int code = connection.getResponseCode();
        if (code == 200) {
            in = connection.getInputStream();
        }else {
            throw new ErrorResponseCodeException("error code is"+code);
        }

        return in;
    }
}