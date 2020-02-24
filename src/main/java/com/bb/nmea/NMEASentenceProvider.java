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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The core driver class for the NMEA sentence processing/parsing logic.  This class
 * is constructed with a set of {@link com.bb.nmea.DataProver} instances to accept 
 * raw NMEA data from and a set of {@link com.bb.nmea.NMEAListener} instances to provide 
 * the NMEA data to.  Each raw NMEA sentence read from the data providers is parsed and 
 * provided to the listeners as am instance of the abstract class {@link com.bb.nmea.NMEASentence}.
 * 
 * @author Scott Stanley
 */
public class NMEASentenceProvider {
    private static final Logger LOG = LogManager.getLogger(NMEASentenceProvider.class);
    
    private List<DataProvider> m_dataProviders = new ArrayList<DataProvider>();
    private SentenceReaderThreadGroup m_thrdGrp = new SentenceReaderThreadGroup();
    private List<NMEAListener> m_listeners = new ArrayList<NMEAListener>();
    
    private long m_numSentences = 0L;
    private long m_startTimestamp;

    /**
     * Create a new NMEASentenceProvider accepting data from the provided set of
     * DataProviders.
     * 
     * @param rawDataProviders The DataProviders to process data from.
     * @throws NMEASentenceProviderException
     */
    public NMEASentenceProvider(final DataProvider... rawDataProviders) 
        throws NMEASentenceProviderException {

        try {
            for (DataProvider dp : rawDataProviders) {
                m_dataProviders.add(dp);
                
                PipedInputStream inpStrm = new PipedInputStream();
                PipedOutputStream outStrm = new PipedOutputStream(inpStrm);
                dp.setOutputStream(outStrm);
                
                BufferedReader rdr = new BufferedReader(new InputStreamReader(inpStrm));

                SentenceReaderRunnable sentRdrRunn = new SentenceReaderRunnable(rdr, this);
                m_thrdGrp.createStartThreadForRunnable(sentRdrRunn);
            }
            
            m_thrdGrp.waitOnAllAlive();
        } catch (IOException e) {
            stop();
            throw new NMEASentenceProviderException("Failed to setup data pipes", e);
        }
    }
    
    /**
     * Start processing data from the DataProviders.  This method will return once data processing has been
     * started allowing the client application to control the main thread of execution.  Processing will
     * continue until the method stop() is called.
     * 
     * @throws NMEASentenceProviderException If an error occurs starting one of the DataProviders.
     */
    public void start() 
            throws NMEASentenceProviderException {
        LOG.info("Starting data providers");
        try {
            m_startTimestamp = System.currentTimeMillis();
            for (DataProvider dp : m_dataProviders) {
                dp.start();
            }
        } catch (DataProviderException e) {
            stop();
            throw new NMEASentenceProviderException("Failed starting data provider", e);
        }
    }
    
    /**
     * Stop the processing of this NMEASentenceProvider.
     * 
     * @throws NMEASentenceProviderException
     */
    public void stop() 
            throws NMEASentenceProviderException {
        
        //
        // Indicate to the data providers that they should stop providing data
        //
        LOG.info("Stopping all data providers");
        for (DataProvider dp : m_dataProviders) {
            try {
                dp.stopDataProvider();
            } catch (DataProviderException e) {
                LOG.info("Error shutting down: " + e.getMessage());
            }
        }

        //
        // Interrupt all of the reader threads and wait for them to complete.  
        // Stopping the data providers above should cause all of the reader threads
        // to complete since the output portion of the piped streams is closed.
        // However, we interrupt here just in case.
        //
        LOG.info("Stopping all sentence readers");
        try {
            m_thrdGrp.interrupt();
            m_thrdGrp.joinAll();
            m_thrdGrp.destroy();
            m_thrdGrp = null;
        } catch (InterruptedException e) {
            // Ignore being interrupted
        }
        
        //
        // Stop all of the listeners
        //
        LOG.info("Stopping all listeners");
        for (NMEAListener l : m_listeners) {
            l.stop();
        }
    }
    
    /**
     * Provide a new event.
     * 
     * @param sent The new NMEASentence
     */
    protected void newEvent(final NMEASentence sent) {
        m_listeners.forEach((NMEAListener l) -> {
            try {
                l.processEvent(sent);
            } catch (Throwable t) {
                LOG.error("Error notifying listener of NMEA sentence", t);
            }
        });
        
        m_numSentences++;
        if ((m_numSentences % 300L) == 0) {
            // Prevent divide by zero when operating with no delay
            double elapsedTimeSec = Math.max((System.currentTimeMillis() - m_startTimestamp)/1000.0, 1.0);
            
            double rate = m_numSentences/elapsedTimeSec; 
            DecimalFormat format = new DecimalFormat("###,###.00");
            LOG.info("Stats: " + m_numSentences + " in " + elapsedTimeSec + " seconds (" + format.format(rate) + " sent/sec)");
        }
    }
    
    /**
     * Add a new NMEAListener to this NMEASentenceProvider.
     * 
     * @param listener The new listener
     */
    public void addListener(final NMEAListener listener) {
        m_listeners.add(listener);
    }
}
