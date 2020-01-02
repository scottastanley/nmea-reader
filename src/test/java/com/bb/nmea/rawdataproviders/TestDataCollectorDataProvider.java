package com.bb.nmea.rawdataproviders;

public class TestDataCollectorDataProvider extends DataProvider {
    DataCollectorOutputStream m_oStrm = new DataCollectorOutputStream();

    public TestDataCollectorDataProvider() {
        m_oStrm = new DataCollectorOutputStream();
        this.setOutputStream(m_oStrm);
    }

    @Override
    protected void start() throws DataProviderException {
    }

    @Override
    protected void stop() throws DataProviderException {
    }

    /**
     * @return the collectedBytes
     */
    public byte[] getCollectedBytes() {
        return m_oStrm.getCollectedBytes();
    }
}
