package com.bestbuy.demo.utils;

import java.io.ByteArrayInputStream;

public class StreamUtil {
    public static ByteArrayInputStream bufferToInputStream(byte[] buffer) {
        return new ByteArrayInputStream(buffer);
    }
}
