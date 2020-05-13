package com.bb.nmea.sentences.common;

import junit.framework.Assert;

public class LatLongTest {
    
    public static void validateLatLong(final String msg, final Integer degrees, final Float minutes, final LatLong l) {
        Assert.assertEquals(msg + ": degrees", degrees, l.getDegrees());
        Assert.assertEquals(msg + ": minutes", minutes, l.getMinutes());
    }
}
