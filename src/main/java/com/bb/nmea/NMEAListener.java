package com.bb.nmea;

public interface NMEAListener {
    public void processEvent(final NMEASentence sentence);
}
