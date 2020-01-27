package com.bb.nmea;

public class NMEADataProcessorException extends Exception {
    private static final long serialVersionUID = -7644757875982384110L;

    public NMEADataProcessorException(String message) {
        super(message);
    }

    public NMEADataProcessorException(String message, Throwable cause) {
        super(message, cause);
    }
}
