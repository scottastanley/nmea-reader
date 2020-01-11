package com.bb.nmea;

import java.io.PipedOutputStream;

import com.bb.nmea.DataProvider;

abstract public class DataProviderTestTools {

    public DataProviderTestTools() {
    }
    
    public static void setOutputStream(final DataProvider dp, final PipedOutputStream oStrm) {
        dp.setOutputStream(oStrm);
    }
}
