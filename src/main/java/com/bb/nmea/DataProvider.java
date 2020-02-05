/*
 * Copyright 2020 Scott Alan Stanley
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bb.nmea;

import java.io.IOException;
import java.io.PipedOutputStream;

import org.apache.log4j.Logger;

/**
 * The abstract base class for all raw NMEA DataProviders.  All data sources
 * used by the NMEASentenceProvider must extend this base class.  This class 
 * provides the core functionality used by the NMEASentenceProvider to link
 * data provided by the data provider over to the threads parsing
 * this data and providing events to the listeners.  
 * 
 * Two methods must be implemented by each DataProvider, start() and stop().
 * These methods will be called to initiate processing of data by the provider 
 * and then to halt processing.  During the processing of data, the data provider
 * logic should call the method provideData(byte[], int, int) with all 
 * data received.
 */
public abstract class DataProvider {
    private static final Logger LOG = Logger.getLogger(DataProvider.class);
    private PipedOutputStream m_oStrm;

    /**
     * Create an instance of a DataProvider.
     */
    public DataProvider() {
    }
    
    /**
     * Initiate the process for reading data.
     * 
     * @throws DataProviderException If an error occurs starting processing
     */
    abstract public void start() throws DataProviderException;
    
    /**
     * Halt the processing of data.  Note that after this method has been
     * called, the stream used to provide data to the parsing logic will
     * be closed, so all calls to provideData() will result in an runtime
     * error.
     * 
     * @throws DataProviderException If an error occurs stopping the process
     */
    abstract public void stopChild() throws DataProviderException;
    
    /**
     * Stop the DataProvider
     * 
     * @throws DataProviderException If an error occurs
     */
    final void stopDataProvider() throws DataProviderException {
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
    final void setOutputStream(final PipedOutputStream oStrm) {
        m_oStrm = oStrm;
    }
    
    /**
     * Provide raw data to be passed in to the system.
     * 
     * @param bytes The raw data bytes
     * @throws DataProviderException If an error occurs feeding this data in to the processor 
     */
    final protected void provideData(final byte[] bytes, final int offset, final int numBytes) 
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
