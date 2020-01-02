package com.bb.nmea_reader;

public class NMEAReaderException 
        extends Exception {
    private static final long serialVersionUID = -1266350326436678596L;

    public NMEAReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public NMEAReaderException(String message) {
        super(message);
    }
}
