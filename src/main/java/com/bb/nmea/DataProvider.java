package com.bb.nmea;

import java.io.IOException;
import java.io.PipedOutputStream;

import com.bb.nmea.rawdataproviders.DataProviderException;

public abstract class DataProvider {
    private PipedOutputStream m_oStrm;
    private Boolean m_failed = false;

    public DataProvider() {
    }
    
    /**
     * Initiate the process for reading data.
     * 
     * @throws DataProviderException
     */
    abstract public void start() throws DataProviderException;
    
    /**
     * Halt the processing
     * 
     * @throws DataProviderException
     */
    abstract public void stop() throws DataProviderException;

    /**
     * Set the output stream that this data provided should feed data in to.
     * 
     * @param oStrm The piped output stream
     */
    protected void setOutputStream(final PipedOutputStream oStrm) {
        m_oStrm = oStrm;
    }
    
    /**
     * Provide raw data to be passed in to the system.
     * 
     * @param bytes The raw data bytes
     * @throws DataProviderException If an error occurs feeding this data in to the processor 
     */
    public void provideData(final byte[] bytes, final int offset, final int numBytes) 
            throws DataProviderException {
        if (m_oStrm == null) {
            throw new RuntimeException("DataProvider not fully configured.  No oStrm provided.");
        }
        
        try {
            m_oStrm.write(bytes, offset, numBytes);
        } catch (IOException e) {
            throw new DataProviderException("Failed to write data to buffer", e);
        }
    }
    
    /**
     * 
     */
    public void setFailed() {
        m_failed = true;
    }
}
