package com.bb.nmea.rawdataproviders;

import org.apache.log4j.Logger;

import com.bb.nmea.DataProvider;
import com.bb.nmea.DataProviderException;

public class TestPassThroughDataProvider extends DataProvider {
    private static final Logger LOG = Logger.getLogger(TestPassThroughDataProvider.class);
    final byte[][] m_testByteArrays;
    
    public TestPassThroughDataProvider(final byte[][] testByteArrays) {
        m_testByteArrays = testByteArrays;
    }

    @Override
    public void start() throws DataProviderException {
        LOG.debug("Starting");
        for (byte[] b : m_testByteArrays) {
            LOG.debug("Providing data: " + new String(b));
            this.provideData(b, 0, b.length);
        }
        LOG.debug("Done");
    }

    @Override
    public void stopChild() throws DataProviderException {
        LOG.debug("Stopping");
    }
}
