package com.bb.nmea_reader;

public interface NMEAListener {
    
    public void newSentence(final String str);
}
