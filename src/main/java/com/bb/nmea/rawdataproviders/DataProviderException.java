package com.bb.nmea.rawdataproviders;

public class DataProviderException extends Exception {
    private static final long serialVersionUID = 2775519156361665959L;

    public DataProviderException(String message) {
        super(message);
    }

    public DataProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}
