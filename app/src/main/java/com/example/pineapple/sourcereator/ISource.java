package com.example.pineapple.sourcereator;


import com.example.pineapple.ticketexceptions.ErrorResponseCodeException;

import java.io.IOException;
import java.io.InputStream;

public interface ISource {
    InputStream getInputStream(String path) throws IOException, ErrorResponseCodeException, ErrorResponseCodeException;
}
