package com.bb.nmea.rawdataproviders;

import java.io.PipedOutputStream;

abstract public class DataProviderTestTools {

    public DataProviderTestTools() {
    }
    
    public static void setOutputStream(final DataProvider dp, final PipedOutputStream oStrm) {
        dp.setOutputStream(oStrm);
    }
}
