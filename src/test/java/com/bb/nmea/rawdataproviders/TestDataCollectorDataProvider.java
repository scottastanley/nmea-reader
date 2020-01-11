package com.bb.nmea.rawdataproviders;

import com.bb.nmea.DataProvider;
import com.bb.nmea.DataProviderException;

public class TestDataCollectorDataProvider extends DataProvider {
    DataCollectorOutputStream m_oStrm = new DataCollectorOutputStream();

    public TestDataCollectorDataProvider() {
        m_oStrm = new DataCollectorOutputStream();
        this.setOutputStream(m_oStrm);
    }

    @Override
    public void start() throws DataProviderException {
    }

    @Override
    public void stop() throws DataProviderException {
    }

    /**
     * @return the collectedBytes
     */
    public byte[] getCollectedBytes() {
        return m_oStrm.getCollectedBytes();
    }
}
