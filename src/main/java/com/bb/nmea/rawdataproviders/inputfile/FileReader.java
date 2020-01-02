package com.bb.nmea.rawdataproviders.inputfile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

import com.bb.nmea.rawdataproviders.DataProvider;
import com.bb.nmea.rawdataproviders.DataProviderException;

public class FileReader implements Runnable {
    private static final Logger LOG = Logger.getLogger(FileReader.class);
    private final DataProvider m_dp;
    private final InputStream m_inpStrm;
    private final CountDownLatch m_latch;
    
    private Integer m_maxBytesPerBlock = 64;
    private Long m_delay = 10L;

    public FileReader(final DataProvider dp, final InputStream inpStrm, 
                      final CountDownLatch latch) {
        m_dp = dp;
        m_inpStrm = inpStrm;
        m_latch = latch;
    }

    @Override
    public void run() {
        Random ran = new Random(System.currentTimeMillis());
        
        // Initialize the buffer
        int numBytesToRead = ran.nextInt(m_maxBytesPerBlock) + 1;
        LOG.debug("Num bytes to read: " + numBytesToRead);
        byte[] buffer = new byte[numBytesToRead];
        
        int numBytesRead = 0;
        try {
            while ((numBytesRead = m_inpStrm.read(buffer)) != -1) {
                m_dp.provideData(buffer, 0, numBytesRead);
                
                // Change the buffer size
                numBytesToRead = ran.nextInt(m_maxBytesPerBlock) + 1;
                LOG.debug("Num bytes to read: " + numBytesToRead);
                buffer = new byte[numBytesToRead];

                if (m_delay >= 0) {
                    Thread.sleep(m_delay);
                }
            }
        } catch (IOException e) {
            LOG.error("Failed reading source file", e);
            m_dp.setFailed();
        } catch (DataProviderException e) {
            LOG.error("Failed providing data", e);
            m_dp.setFailed();
        } catch (InterruptedException e) {
            LOG.info("Thread interrupted");
            // Ignore this one since this is thrown in the natural stop process
        } finally {
            
            // Close the input stream
            if (m_inpStrm != null) {
                try {
                    LOG.info("Closing input stream");
                    m_inpStrm.close();
                } catch (IOException ex) {}
            }
            
            // Count down on the latch to indicate we completed
            m_latch.countDown();
        }
    }

    /**
     * @return the maxBytesPerBlock
     */
    public Integer getMaxBytesPerBlock() {
        return m_maxBytesPerBlock;
    }

    /**
     * @param maxBytesPerBlock the maxBytesPerBlock to set
     */
    public void setMaxBytesPerBlock(Integer maxBytesPerBlock) {
        m_maxBytesPerBlock = maxBytesPerBlock;
    }

    /**
     * @return the delay
     */
    public Long getDelay() {
        return m_delay;
    }

    /**
     * @param delay the delay to set
     */
    public void setDelay(Long delay) {
        m_delay = delay;
    }
}
