package com.bb.nmea.rawdataproviders;

public class TestPassThroughDataProvider extends DataProvider {

    public TestPassThroughDataProvider() {
    }
    
    public void passThroughBytes(final byte[] testBytes) 
            throws DataProviderException {
        this.provideData(testBytes, 0, testBytes.length);
    }

    @Override
    public void start() throws DataProviderException {
    }

    @Override
    public void stop() throws DataProviderException {
    }
}
