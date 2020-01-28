package com.bb.nmea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class NMEASentenceProvider {
    private static final Logger LOG = Logger.getLogger(NMEASentenceProvider.class);
    
    private List<DataProvider> m_dataProviders = new ArrayList<DataProvider>();
    private SentenceReaderThreadGroup m_thrdGrp = new SentenceReaderThreadGroup();
    private List<NMEAListener> m_listeners = new ArrayList<NMEAListener>();

    /**
     * Create a new NMEASentenceProvider accepting data from the provided set of
     * DataProviders.
     * 
     * @param rawDataProviders The DataProviders to process data from.
     * @throws NMEADataProcessorException
     */
    public NMEASentenceProvider(final DataProvider... rawDataProviders) 
        throws NMEADataProcessorException {

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
            throw new NMEADataProcessorException("Failed to setup data pipes", e);
        }
    }
    
    /**
     * Start processing data from the DataProviders.
     * 
     * @throws NMEADataProcessorException If an error occurs starting one of the DataProviders.
     */
    public void start() 
            throws NMEADataProcessorException {
        LOG.info("Starting data providers");
        try {
            for (DataProvider dp : m_dataProviders) {
                dp.start();
            }
        } catch (DataProviderException e) {
            stop();
            throw new NMEADataProcessorException("Failed starting data provider", e);
        }
    }
    
    /**
     * Stop the processing of this NMEASentenceProvider.
     * 
     * @throws NMEADataProcessorException
     */
    public void stop() 
            throws NMEADataProcessorException {
        
        //
        // Indicate to the data providers that they should stop providing data
        //
        LOG.info("Stopping all data providers");
        for (DataProvider dp : m_dataProviders) {
            try {
                dp.stop();
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
