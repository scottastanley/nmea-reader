package com.bb.nmea;

import java.io.IOException;
import java.io.PipedOutputStream;

import org.apache.log4j.Logger;

public abstract class DataProvider {
    private static final Logger LOG = Logger.getLogger(DataProvider.class);

    private PipedOutputStream m_oStrm;

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
    abstract public void stopChild() throws DataProviderException;
    
    /**
     * Stop the DataProvider
     * 
     * @throws DataProviderException If an error occurs
     */
    public void stop() throws DataProviderException {
        // Allow the child to stop itself
        stopChild();
        
        // Close the output stream
        try {
            m_oStrm.flush();
            m_oStrm.close();
        } catch (IOException e) {}
        m_oStrm = null;
    }

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
            LOG.debug("Writing data: " + new String(bytes, offset, numBytes));
            m_oStrm.write(bytes, offset, numBytes);
        } catch (IOException e) {
            throw new DataProviderException("Failed to write data to buffer", e);
        }
    }
}
