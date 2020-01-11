package com.bb.nmea.rawdataproviders.inputfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

import com.bb.nmea.rawdataproviders.DataProvider;
import com.bb.nmea.rawdataproviders.DataProviderException;

public class InputFileDataProvider 
        extends DataProvider {
    private static final Logger LOG = Logger.getLogger(InputFileDataProvider.class);

    private final File m_inputFile;
    private Thread m_readThread = null;
    private CountDownLatch m_latch;

    public InputFileDataProvider(final File inputFile) {
        m_inputFile = inputFile;
    }

    @Override
    public void start() throws DataProviderException {
        LOG.info("Starting...");
        
        // Verify that the input file exists and is indeed a file
        if (! m_inputFile.exists() || ! m_inputFile.isFile()) {
            throw new DataProviderException("Provided input file does not exist or is not a file [" +
                                            m_inputFile.getAbsolutePath() + "]");
        }
    
        try {
            InputStream inpStrm = new FileInputStream(m_inputFile);
            
            m_latch = new CountDownLatch(1);
            m_readThread = new Thread(new FileReader(this, inpStrm, m_latch));
            m_readThread.setName("NMEA File Input");
            
            // Start the read thread
            m_readThread.start();
            LOG.info("Started read thread.");
            
            LOG.info("Waiting on read thread to complete");
            m_latch.await();
            
            LOG.info("Read thread completed...");
            m_readThread = null;
        } catch (FileNotFoundException e) {
            throw new DataProviderException("Provided input file does not exist [" + 
                                            m_inputFile.getAbsolutePath() + "]", e);
        } catch(InterruptedException e) {
            LOG.info("InputFileDataProvider was interrupted");
        }
    }

    @Override
    public void stop() throws DataProviderException {
        LOG.info("Stopping...");
        // Interrupt the read thread
        if (m_readThread != null) {
            m_readThread.interrupt();
            m_readThread = null;
        }

        LOG.info("Read thread interrupted");
    }
}
